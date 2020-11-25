package com.demo.mydemo.mp5.injector;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

/**
 * 自定义全局操作
 */
public class MySqlInjector extends AutoSqlInjector {
    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = "delete from "+table.getTableName();
        //注入的方法名一定要与 EmployeeMapper接口中的方法名一致
        String method = "deleteAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration,sql,modelClass);
        this.addDeleteMappedStatement(mapperClass,method,sqlSource);
    }
}
