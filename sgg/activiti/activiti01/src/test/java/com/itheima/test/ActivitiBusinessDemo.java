package com.itheima.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ActivitiBusinessDemo {

    /**
     * 添加业务key 到 activity 的表
     */
    @Test
    public void addBusinessKey() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 启动流程实例的过程中，添加businessKey
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection", "1001");
        System.out.println("businessKey==" + instance.getBusinessKey());
    }

    /**
     * 全部流程实例的挂起和激活
     * suspend 暂停
     */
    @Test
    public void suspendAllProcessInstance() {
        // org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration    activiti.cfg.xml配置文件中配置的对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 查询流程定义
        ProcessDefinition processDefinition = repositoryService.
                createProcessDefinitionQuery()
                .processDefinitionKey("myEvection")
                .singleResult();
        // 获取当前流程定义的实例是否都是挂起的状态
        boolean suspended = processDefinition.isSuspended();
        // 获取流程定义的id
        String definitionId = processDefinition.getId();
        // 如果是挂起状态，改为激活状态
        if (suspended) {
            // 参数1：流程定义id 参数2：是否激活 参数3：激活时间
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
            System.out.println("流程定义id："+definitionId+"，已激活");
        } else {
            // 如果是激活状态，改为挂起状态
            // 参数1：流程定义id 参数2：是否暂停 参数3：暂停时间
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
            System.out.println("流程定义id："+definitionId+"，已挂起");
        }
    }

    /**
     * 挂起、激活单个流程实例
     */
    @Test
    public void suspendSingleProcessInstance(){
        String processInstanceId = "30001";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        boolean suspended = instance.isSuspended();
        if(suspended){
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例id："+instance.getId()+"已经暂停");
        }else{
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例id："+instance.getId()+"已经激活");
        }
    }

    /**
     * 完成个人任务
     */
    @Test
    public void completeTask(){
        // 获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 获取TaskService
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId("30001")
                .taskAssignee("zhangsan").singleResult();
        taskService.complete(task.getId());
    }

}
