package common.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface BaseMapper<T> {
    /**
     * 添加一条记录
     *
     * @param obj 要插入的对象
     * @return
     */
    @InsertProvider(type = EntityProvider.class, method = "insert")
    int insert(@Param("obj") T obj);

    /**
     * 一次插入多条记录
     *
     * @param list
     * @return
     */
    @InsertProvider(type = EntityProvider.class, method = "insertList")
    int insertList(@Param("list") java.util.List<T> list);

    /**
     * 通过主键更新记录
     *
     * @param obj 要更新的对象
     * @return
     */
    @UpdateProvider(type = EntityProvider.class, method = "updateByPrimaryKey")
    int updateByPrimaryKey(@Param("obj") Object obj);

    /**
     * 更新指定列通过指定的条件
     *
     * @param propertyColumns 要更新的列，用,分隔
     * @param whereColumns    条件列，用,分隔
     * @param obj             要更新的对象
     * @return
     */
    @UpdateProvider(type = EntityProvider.class, method = "update")
    int update(@Param("param1") Object obj, @Param("param2") String propertyColumns, @Param("param3") String whereColumns);

    /**
     * 删除
     * @param obj
     * @param whereColumns
     * @return
     */
    @UpdateProvider(type=EntityProvider.class,method = "delete")
    int delete(@Param("param1") Object obj,@Param("param2") String whereColumns);

    /**
     * 通过主键查询
     *
     * @param obj
     * @return
     */
    @SelectProvider(type = EntityProvider.class, method = "selectOneByPrimaryKey")
    T selectOneByPrimaryKey(@Param("param1") Object obj);

    /**
     * 通过指定列查询
     *
     * @param obj
     * @param propertyColumns
     * @return
     */
    @SelectProvider(type = EntityProvider.class, method = "selectOne")
    T selectOne(@Param("param1") Object obj, @Param("param2") String propertyColumns);

    @SelectProvider(type = EntityProvider.class, method = "selectList")
    java.util.List<T> selectList(@Param("param1") Object obj, @Param("param2") String propertyColumns);
}
