package common.mapper;

import common.annotation.Column;
import common.annotation.Id;
import common.annotation.Table;
import common.annotation.Transient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class EntityProvider {
    public String insert(@Param("obj") Object obj) throws Exception {
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        String tableName = getTableName(obj);
        String[] getterNames = getterNames(obj);
        StringBuilder sb = new StringBuilder("insert into " + tableName);
        StringBuilder cols = new StringBuilder("(");
        StringBuilder values = new StringBuilder(" values(");
        int index = 0;
        for (int i = 0; i < getterNames.length; i++) {
            String propertyName = getterNames[i];
            Object value = metaObject.getValue(propertyName);
            String columnName = getColumnName(propertyName, obj);
            if (value != null && !value.equals("") && columnName != null) {
                cols.append(index == 0 ? columnName : ("," + columnName));
                values.append(index == 0 ? ("#{" + propertyName + "}") : (",#{" + propertyName + "}"));
                index++;
            }
        }
        cols.append(")");
        values.append(")");
        sb.append(cols).append(values);
        return sb.toString();
    }

    public String insertList(Map map) throws Exception {
        List<Object> objs = (List<Object>) map.get("list");
        if (objs.size() == 0) {
            return "";
        }
        MetaObject metaObject = SystemMetaObject.forObject(objs.get(0));
        String tableName = getTableName(objs.get(0));
        String[] getterNames = getterNames(objs.get(0));
        StringBuilder sb = new StringBuilder("insert all");

        for (int j = 0; j < objs.size(); j++) {
            StringBuilder cols = new StringBuilder();
            StringBuilder values = new StringBuilder();
            Object obj = objs.get(j);
            cols.append(" into " + tableName + "(");
            int index = 0;
            values.append(" values (");
            for (int i = 0; i < getterNames.length; i++) {
                String propertyName = getterNames[i];
                Object value = metaObject.getValue(propertyName);
                String columnName = getColumnName(propertyName, obj);
                propertyName = "list[" + j + "]." + propertyName;
                if (value != null && !value.equals("") && columnName != null) {
                    cols.append(index == 0 ? columnName : ("," + columnName));
                    values.append(index == 0 ? ("#{" + propertyName + "}") : (",#{" + propertyName + "}"));
                    index++;
                }
            }
            values.append(")");
            cols.append(")");
            cols.append(values);
            sb.append(cols);
        }
        sb.append(" select 1 from dual");
        return sb.toString();
    }

    public String updateByPrimaryKey(@Param("obj") Object obj) throws Exception {
        String tableName = getTableName(obj);
        String[] getterNames = getterNames(obj);
        StringBuilder sb = new StringBuilder("update " + tableName + " set ");
        StringBuilder colsValues = new StringBuilder();
        StringBuilder where = new StringBuilder(" where ");
        int columnIndex = 0;
        int whereIndex = 0;
        for (int i = 0; i < getterNames.length; i++) {
            String propertyName = getterNames[i];
            Object value = getPropertyValue(propertyName, obj);
            Field field = this.getSField(propertyName, obj);
            Id id = field.getAnnotation(Id.class);
            String columnName = getColumnName(propertyName, obj);
            if (value != null && !value.equals("") && columnName != null) {
                if (id != null) {
                    where.append(whereIndex == 0 ? columnName + "=" + ("#{" + propertyName + "}") : (" and " + columnName + "=" + ("#{" + propertyName + "}")));
                    whereIndex++;
                } else {
                    colsValues.append(columnIndex == 0 ? columnName + "=" + ("#{" + propertyName + "}") : ("," + columnName + "=" + ("#{" + propertyName + "}")));
                    columnIndex++;
                }
            }
        }
        sb.append(colsValues).append(where);
        return sb.toString();
    }

    //update(obj,"id,name","id,name")
    public String update(@Param("param1") Object obj, @Param("param2") String propertyColumns, @Param("param3") String whereColumns) throws Exception {
        String[] propertyColumnsArr = propertyColumns.split(",");
        if (propertyColumns.equals("all")) {
            propertyColumnsArr = getterNames(obj);
        }
        String[] whereColumnsArr = whereColumns.split(",");
        String tableName = getTableName(obj);
        StringBuilder sb = new StringBuilder("update " + tableName + " set ");
        String set = buildSet(Arrays.asList(propertyColumnsArr), obj, "param1.");
        String where = buildWhere(Arrays.asList(whereColumnsArr), obj, "param1.");
        sb.append(set).append(where);
        return sb.toString();
    }

    //delete(obj,whereColumns)
    public String delete(@Param("param1") Object obj, @Param("param2") String whereColumns) throws Exception {
        String[] whereColumnsArr = whereColumns.split(",");
        String tableName = getTableName(obj);
        StringBuilder sb = new StringBuilder("delete from " + tableName);
        String where = buildWhere(Arrays.asList(whereColumnsArr), obj, "param1.");
        sb.append(where);
        return sb.toString();
    }

    public String selectOneByPrimaryKey(@Param("param1") Object obj) throws Exception {
        String primaryKey = getPrimaryProperty(obj);
        String tableName = getTableName(obj);
        String where = buildWhere(Arrays.asList(primaryKey), obj, "");
        String columnAliases = buildColumnAliases(obj);
        String sql = "select " + tableName + ".*" + " from " + tableName + where;
        if (StringUtils.isNotEmpty(columnAliases)) {
            sql = "select " + tableName + ".*," + columnAliases + " from " + tableName + where;
        }
        return sql;
    }

    public String selectOne(@Param("param1") Object obj, @Param("param2") String whereColumns) throws Exception {
        String[] ws = whereColumns.split(",");
        String tableName = getTableName(obj);
        String where = buildWhere(Arrays.asList(ws), obj, "param1.");
        String columnAliases = buildColumnAliases(obj);
        if (ws.length == 0) {
            where = "";
        }
        String sql = "select " + tableName + ".*" + " from " + tableName + where;
        if (StringUtils.isNotEmpty(columnAliases)) {
            sql = "select " + tableName + ".*," + columnAliases + " from " + tableName + where;
        }
        return sql;
    }

    public String selectList(@Param("param1") Object obj, @Param("param2") String whereColumns) throws Exception {
        return selectOne(obj, whereColumns);
    }

    /**
     * 获取主键属性名
     *
     * @return
     */
    protected String getPrimaryProperty(Object obj) throws Exception {
        String[] propertyNames = getterNames(obj);
        for (String propertyName : propertyNames) {
            Field field = this.getSField(propertyName, obj);
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                return propertyName;
            }
        }
        return "uuid";
    }

    /**
     * 获取表名
     *
     * @param obj
     * @return
     */
    protected String getTableName(Object obj) {
        String tableName = obj.getClass().getAnnotation(Table.class) != null ? ((Table) obj.getClass().getAnnotation(Table.class)).name() : null;
        tableName = tableName != null ? tableName : obj.getClass().getSimpleName();
        return tableName;
    }

    /**
     * 获取所有属性
     *
     * @param obj
     * @return
     */
    protected String[] getterNames(Object obj) {
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        String[] getterNames = metaObject.getGetterNames();
        return getterNames;
    }

    /**
     * 通过属性名获取列名,如果有Transient注解，则返回空
     *
     * @param propertyName
     * @param obj
     * @return
     * @throws
     */
    protected String getColumnName(String propertyName, Object obj) throws Exception {
        Field field = this.getSField(propertyName, obj);
        boolean b = field.getAnnotation(Transient.class) == null;
        if (b) {
            String columnName = field.getAnnotation(Column.class) != null ? field.getAnnotation(Column.class).name() : null;
            columnName = columnName != null ? columnName : propertyName;
            return columnName;
        }
        return null;
    }

    /**
     * 获取属性配置对应注解配置的列名
     *
     * @param propertyName
     * @param obj
     * @return
     */
    protected String getAnnColumn(String propertyName, Object obj) throws Exception {
        Field field = this.getSField(propertyName, obj);
        boolean b = field.getAnnotation(Transient.class) == null;
        if (!b) {
            return null;
        }
        String columnName = field.getAnnotation(Column.class) != null ? field.getAnnotation(Column.class).name() : null;
        return columnName;
    }

    /**
     * 获取字段，包括超类的
     *
     * @param propertyName
     * @param obj
     * @return
     */
    protected Field getSField(String propertyName, Object obj) throws Exception {
        Field field = getSpFiled(propertyName, obj.getClass());
        if (field == null) {
            field = getSpFiled(propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length()), obj.getClass());
        }
        return field;
    }

    private Field getSpFiled(String propertyName, Class cls) {
        if (cls == Object.class) {
            return null;
        }
        Field field = null;
        try {
            field = cls.getDeclaredField(propertyName);
        } catch (NoSuchFieldException e) {
            field = getSpFiled(propertyName, cls.getSuperclass());
        }
        return field;
    }

    protected Object getPropertyValue(String propertyName, Object obj) {
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        Object value = metaObject.getValue(propertyName);
        return value;
    }

    protected String buildSet(List<String> propertyColumns, Object obj, String suffix) throws Exception {
        MetaObject metaObject = SystemMetaObject.forObject(obj);
        StringBuilder colsValues = new StringBuilder();
        int columnIndex = 0;
        for (int i = 0; i < propertyColumns.size(); i++) {
            String propertyName = propertyColumns.get(i);
            Object value = metaObject.getValue(propertyName);
            String columnName = getColumnName(propertyName, obj);
            if (value != null && !value.equals("") && columnName != null) {
                propertyName = suffix + propertyName;
                colsValues.append(columnIndex == 0 ? columnName + "=" + ("#{" + propertyName + "}") : ("," + columnName + "=" + ("#{" + propertyName + "}")));
                columnIndex++;
            }
        }
        return colsValues.toString();
    }

    protected String buildWhere(List<String> whereColumns, Object obj, String suffix) throws Exception {
        StringBuilder where = new StringBuilder(" where ");
        int whereIndex = 0;
        for (int i = 0; i < whereColumns.size(); i++) {
            String propertyName = whereColumns.get(i);
            String columnName = getColumnName(propertyName, obj);
            if (columnName != null) {
                propertyName = suffix + propertyName;
                where.append(whereIndex == 0 ? columnName + "=" + ("#{" + propertyName + "}") : (" and " + columnName + "=" + ("#{" + propertyName + "}")));
                whereIndex++;
            }
        }
        return where.toString();
    }

    protected String buildColumnAliases(Object obj) throws Exception {
        StringBuilder columnAliases = new StringBuilder();
        String[] propertyNames = getterNames(obj);
        for (String propertyName : propertyNames) {
            String annColumn = getAnnColumn(propertyName, obj);
            if (StringUtils.isNotEmpty(annColumn) && !annColumn.equals(propertyName)) {
                columnAliases.append(annColumn + " " + propertyName + ",");
            }
        }
        String columnAliasesStr = columnAliases.toString();
        if (!"".equals(columnAliasesStr)) {
            columnAliasesStr = columnAliasesStr.substring(0, columnAliasesStr.length() - 1);
        }
        return columnAliasesStr;
    }

}
