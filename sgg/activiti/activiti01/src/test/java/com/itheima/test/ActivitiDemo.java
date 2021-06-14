package com.itheima.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

public class ActivitiDemo {
    @Test
    public void testDeployment(){
        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2、获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3、使用service进行流程的部署，定义一个流程的名字，把bpmn和png部署到数据库中
        Deployment deploy = repositoryService.createDeployment().name("出差申请流程")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();
        // 4、输出部署信息
        System.out.println("流程部署id="+deploy.getId());
        System.out.println("流程部署名字="+deploy.getName());
    }

    /**
     * 使用zip包进行批量的部署
     */
    @Test
    public void deployProcessByZip(){
//        获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        获取RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
//        流程部署
//        读取资源包文件，构造成inputStream
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
//        用inputStream 构造ZipInputStream
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//        使用压缩包的流进行流程的部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("流程部署id="+deploy.getId());
        System.out.println("流程部署的名称="+deploy.getName());
    }

    /**
     * 启动流程实例
     * act_hi_actinst 流程实例执行历史
     * act_hi_identitylink  流程参与者的历史信息
     * act_hi_procinst 流程实例的历史信息
     * act_hi_taskinst 任务的历史信息
     * act_ru_execution 流程执行的信息
     * act_ru_identitylink  流程参与者信息
     * act_ru_task  任务信息
     */
    @Test
    public void testStartProcess(){
        // 1、创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2、获取RunTimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 3、根据流程定义的id启动流程
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("myEvection");
        // 4、输出内容
        System.out.println("流程定义ID："+instance.getProcessDefinitionId());
        System.out.println("流程实例ID："+instance.getId());
        System.out.println("当前活动的ID："+instance.getActivityId());
    }

    /**
     * 查询个人待执行的任务
     */
    @Test
    public void testFindPersonalTaskList(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")  // 流程key
                .taskAssignee("zhangsan").list();   // 要查询的负责人

        for (Task task : taskList) {
            System.out.println("流程实例id="+task.getProcessInstanceId());
            System.out.println("任务id="+task.getId());
            System.out.println("任务负责人="+task.getAssignee());
            System.out.println("任务名称="+task.getName());
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
        // 根据任务id完成任务
        //taskService.complete("2505");
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("rose").singleResult();
        System.out.println("流程实例id="+task.getProcessInstanceId());
        System.out.println("任务id="+task.getId());
        System.out.println("任务负责人="+task.getAssignee());
        System.out.println("任务名称="+task.getName());
        String id = task.getId();
        taskService.complete(id);
    }

    /**
     * 查询流程定义
     */
    @Test
    public void queryProcessDefinition(){
        // 获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 获取 ProcessDifinitionQuery对象
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        // 查询当前所有的流程定义
        List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion()
                .desc().list();
        // 输出信息
        for (ProcessDefinition processDefinition : definitionList) {
            System.out.println("流程定义ID："+processDefinition.getId());
            System.out.println("流程定义名称："+processDefinition.getName());
            System.out.println("流程定义Key："+processDefinition.getKey());
            System.out.println("流程定义版本："+processDefinition.getVersion());
            System.out.println("流程部署ID："+processDefinition.getDeploymentId());
        }
    }

    /**
     * 删除流程部署信息，如果当前流程还未完成，则需要使用级联删除
     */
    @Test
    public void deleteDeployMent(){
        // 获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deploymentId = "1";
        //repositoryService.deleteDeployment(deploymentId);
        // 级联删除
        repositoryService.deleteDeployment(deploymentId,true);
    }

    /**
     * 下载资源文件
     *  方案1：使用activiti提供的api下载资源文件
     *  方案2：自己写代码从数据库下载文件，使用jdbc对blob类型或者clob类型读取出来，保存到文件目录
     *  这里我们使用方案1
     */
    @Test
    public void getDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition("myEvection");
        String pngName = processDefinition.getDiagramResourceName();
        // 获取图片资源
        InputStream pngStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), pngName);
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());

    }

    /**
     * 查询历史
     */
    @Test
    public void findHistoryInfo(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery activityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        activityInstanceQuery.processInstanceId("2501");
        activityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = activityInstanceQuery.list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getProcessDefinitionId());
            System.out.println(historicActivityInstance.getProcessInstanceId());
        }
    }
}
