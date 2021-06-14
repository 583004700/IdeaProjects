package com.itheima.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.junit.Test;

/*
    使用activiti提供的默认方式来创建mysql的表
 */
public class TestCreate {
    @Test
    public void testCreateDbTable(){
        //需要使用activiti提供的工具类 ProcessEngines，使用方法getDefaultProcessEngine
        //getDefaultProcessEngine会默认从resource下读取名字为activiti.cfg.xml的文件
        //创建processEngine时，就会创建mysql的表
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService();
        System.out.println(processEngine);
    }
}
