package com.itheima.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestAssigneeUel {
    @Test
    public void testDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().name("出差申请流程-uel").addClasspathResource("bpmn/evection-uel.bpmn").deploy();
        System.out.println("流程部署id="+deploy.getId());
        System.out.println("流程部署名字="+deploy.getName());
    }

    @Test
    public void startAssigneeUel(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 设定 assignee的值，用来替换 uel 表达式
        Map<String,Object> assigneeMap = new HashMap<String,Object>();
        assigneeMap.put("assignee0","张三");
        assigneeMap.put("assignee1","李经理");
        assigneeMap.put("assignee2","王总经理");
        assigneeMap.put("assignee3","赵财务");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection1",assigneeMap);
    }
}
