package common.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface BaseMapper<T> {
    /**
     * 添加一条记录
     * @param obj 要插入的对象
     * @return
     */
    @InsertProvider(type=EntityProvider.class,method = "insert")
    int insert(T obj);

    /**
     * 一次插入多条记录
     * @param objs
     * @return
     */
    @InsertProvider(type=EntityProvider.class,method = "insertList")
    int insertList(@Param("list") List<T> objs);

    /**
     * 通过主键更新记录
     * @param obj 要更新的对象
     * @return
     */
    @UpdateProvider(type=EntityProvider.class,method = "updateByPrimaryKey")
    int updateByPrimaryKey(Object obj);

    /**
     * 更新指定列通过指定的条件
     * @param propertyColumns 要更新的列，用,分隔
     * @param whereColumns 条件列，用,分隔
     * @param obj   要更新的对象
     * @return
     */
    @UpdateProvider(type=EntityProvider.class,method = "update")
    int update(Object obj, String propertyColumns, String whereColumns);

    /**
     * 通过主键查询
     * @param obj
     * @return
     */
    @SelectProvider(type=EntityProvider.class,method = "selectOneByPrimaryKey")
    T selectOneByPrimaryKey(Object obj);

    /**
     * 通过指定列查询
     * @param obj
     * @param propertyColumns
     * @return
     */
    @SelectProvider(type=EntityProvider.class,method = "selectOne")
    T selectOne(Object obj, String propertyColumns);

    @SelectProvider(type=EntityProvider.class,method = "selectList")
    List<T> selectList(Object obj, String propertyColumns);
}
