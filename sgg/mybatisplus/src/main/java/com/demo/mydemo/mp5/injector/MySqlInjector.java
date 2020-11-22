package com.demo.mydemo.mp5.injector;

import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.builder.MapperBuilderAssistant;

/**
 * 自定义全局操作
 */
public class MySqlInjector extends AutoSqlInjector {
    @Override
    public void inject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        //将EmployeeMapper中定义的deleteAll处理成对应的MappedStatement对象，加入到configuration
    }
}
