package day_21.com.atguigu.jdbc;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTest {
	
	@Test
	public void testJdbcTools() throws Exception{
		Connection connection = JDBCTools.getConnection();
		System.out.println(connection); 
	}
	
	/**
	 * 1. 创建 c3p0-config.xml 文件, 
	 * 参考帮助文档中 Appendix B: Configuation Files 的内容
	 * 2. 创建 ComboPooledDataSource 实例；
	 * DataSource dataSource = 
	 *			new ComboPooledDataSource("helloc3p0");  
	 * 3. 从 DataSource 实例中获取数据库连接. 
	 */
	@Test
	public void testC3poWithConfigFile() throws Exception{
		DataSource dataSource = 
				new ComboPooledDataSource("helloc3p0");  
		
		System.out.println(dataSource.getConnection()); 
		
		ComboPooledDataSource comboPooledDataSource = 
				(ComboPooledDataSource) dataSource;
		System.out.println(comboPooledDataSource.getMaxStatements()); 
	}
	
	@Test
	public void testC3P0() throws Exception{
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:mysql:///atguigu" );
		cpds.setUser("root");                                  
		cpds.setPassword("root");
		
		System.out.println(cpds.getConnection()); 
	}
	
	/**
	 * 1. 加载 dbcp 的 properties 配置文件: 配置文件中的键需要来自 BasicDataSource
	 * 的属性.
	 * 2. 调用 BasicDataSourceFactory 的 createDataSource 方法创建 DataSource
	 * 实例
	 * 3. 从 DataSource 实例中获取数据库连接. 
	 */
	@Test
	public void testDBCPWithDataSourceFactory() throws Exception{
		
		Properties properties = new Properties();
		InputStream inStream = JDBCTest.class.getClassLoader()
				.getResourceAsStream("day_21/dbcp.properties");
		properties.load(inStream);
		
		DataSource dataSource = 
				BasicDataSourceFactory.createDataSource(properties);
		
		System.out.println(dataSource.getConnection()); 
		
//		BasicDataSource basicDataSource = 
//				(BasicDataSource) dataSource;
//		
//		System.out.println(basicDataSource.getMaxWait()); 
	}
	
	/**
	 * 使用 DBCP 数据库连接池
	 * 1. 加入 jar 包(2 个jar 包). 依赖于 Commons Pool
	 * 2. 创建数据库连接池
	 * 3. 为数据源实例指定必须的属性
	 * 4. 从数据源中获取数据库连接
	 * @throws SQLException 
	 */
	@Test
	public void testDBCP() throws SQLException{
		final BasicDataSource dataSource = new BasicDataSource();
		
		//2. 为数据源实例指定必须的属性
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql:///atguigu");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//3. 指定数据源的一些可选的属性.
		//1). 指定数据库连接池中初始化连接数的个数
		dataSource.setInitialSize(5);
		
		//2). 指定最大的连接数: 同一时刻可以同时向数据库申请的连接数
		dataSource.setMaxActive(5);
		
		//3). 指定小连接数: 在数据库连接池中保存的最少的空闲连接的数量 
		dataSource.setMinIdle(2);
		
		//4).等待数据库连接池分配连接的最长时间. 单位为毫秒. 超出该时间将抛出异常. 
		dataSource.setMaxWait(1000 * 5);
		
		//4. 从数据源中获取数据库连接
		Connection connection = dataSource.getConnection();
		System.out.println(connection.getClass()); 
		
		connection = dataSource.getConnection();
		System.out.println(connection.getClass()); 
		
		connection = dataSource.getConnection();
		System.out.println(connection.getClass()); 
		
		connection = dataSource.getConnection();
		System.out.println(connection.getClass()); 
		
		Connection connection2 = dataSource.getConnection();
		System.out.println(">" + connection2.getClass()); 
		
		new Thread(){
			public void run() {
				Connection conn;
				try {
					conn = dataSource.getConnection();
					System.out.println(conn.getClass()); 
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		connection2.close();
	}
	
	@Test
	public void testBatch(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = null;
		
		try {
			connection = JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			sql = "INSERT INTO customers VALUES(?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			Date date = new Date(new java.util.Date().getTime());
			
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 100000; i++){
				preparedStatement.setInt(1, i + 1);
				preparedStatement.setString(2, "name_" + i);
				preparedStatement.setDate(3, date);
				
				//"积攒" SQL 
				preparedStatement.addBatch();
				
				//当 "积攒" 到一定程度, 就统一的执行一次. 并且清空先前 "积攒" 的 SQL
				if((i + 1) % 300 == 0){
					preparedStatement.executeBatch();
					preparedStatement.clearBatch();
				}
			}
			
			//若总条数不是批量数值的整数倍, 则还需要再额外的执行一次. 
			if(100000 % 300 != 0){
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
			}
			
			long end = System.currentTimeMillis();
			
			System.out.println("Time: " + (end - begin)); //569
			
			JDBCTools.commit(connection);
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		} finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	

	@Test
	public void testBatchWithPreparedStatement(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = null;
		
		try {
			connection = JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			sql = "INSERT INTO customers VALUES(?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			Date date = new Date(new java.util.Date().getTime());
			
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 100000; i++){
				preparedStatement.setInt(1, i + 1);
				preparedStatement.setString(2, "name_" + i);
				preparedStatement.setDate(3, date);
				
				preparedStatement.executeUpdate();
			}
			long end = System.currentTimeMillis();
			
			System.out.println("Time: " + (end - begin)); //9819
			
			JDBCTools.commit(connection);
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		} finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	/**
	 * 向  Oracle 的 customers 数据表中插入 10 万条记录
	 * 测试如何插入, 用时最短. 
	 * 1. 使用 Statement.
	 */
	@Test
	public void testBatchWithStatement(){
		Connection connection = null;
		Statement statement = null;
		String sql = null;
		
		try {
			connection = JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			
			statement = connection.createStatement();
			
			long begin = System.currentTimeMillis();
			for(int i = 0; i < 100000; i++){
				sql = "INSERT INTO customers VALUES(" + (i + 1) 
						+ ", 'name_" + i + "', '29-6月 -13')";
				statement.addBatch(sql);
			}
			long end = System.currentTimeMillis();
			
			System.out.println("Time: " + (end - begin)); //39567
			
			JDBCTools.commit(connection);
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		} finally{
			JDBCTools.releaseDB(null, statement, connection);
		}
	}

}
