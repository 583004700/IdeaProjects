package com.itheima.test;

import com.itheima.demo.pojo.Evection;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestVariables {

    @Test
    public void testDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .name("出差申请流程-variables")
                .addClasspathResource("bpmn/evection-global.bpmn")
                .deploy();
        System.out.println("流程部署id="+deploy.getId());
        System.out.println("流程部署名字="+deploy.getName());
    }

    /**
     * 启动流程的时候设置流程变量
     * 设置流程变量 num
     * 设置任务负责人
     */
    @Test
    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String key = "myEvection2";
        // 流程变量的mpa
        Map<String,Object> variables = new HashMap<String,Object>();
        // 设置流程变量
        Evection evection = new Evection();
        evection.setNum(5d);
        // 把流程变量的pojo放入map
        variables.put("evection",evection);
        // 设置任务的负责人
        variables.put("assignee0","李四");
        variables.put("assignee1","王经理");
        variables.put("assignee2","李总经理");
        variables.put("assignee3","张财务");

        runtimeService.startProcessInstanceByKey(key,variables);
    }

    /**
     * 完成个人任务
     */
    @Test
    public void completTask(){
//        流程定义的Key
        String key = "myEvection2";
//        任务负责人
//        String assingee = "李四";

        String assingee = "张财务";
        //        获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取taskservice
        TaskService taskService = processEngine.getTaskService();
//        查询任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assingee)
                .singleResult();
        if(task != null){
            //     根据任务id来   完成任务
            taskService.complete(task.getId());
        }

    }

}
