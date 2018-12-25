package com.imooc.activiti.helloworld;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DemoMain {

    private static final Logger log = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        log.info("start process...");
        //1.创建流程引擎
        ProcessEngine processEngine = getProcessEngine();
        //2.部署流程定义文件
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);
        //3.启动运行流程
        startProcessInstanceByDefinitionId(processEngine, processDefinition);
        //4.处理流程任务
        processTasks(processEngine);
        log.info("end process...");
    }

    private static void processTasks(ProcessEngine processEngine) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().list();
        for (Task task : taskList) {
            log.info("待处理的任务：[{}]", task.getName());
        }
        log.info("待处理的数量： [{}] ", taskList.size());
    }

    private static void startProcessInstanceByDefinitionId(ProcessEngine processEngine, ProcessDefinition processDefinition) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        log.info("启动流程：[{}] ", processInstance.getProcessDefinitionKey());
    }

    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("second_approve.bpmn20.xml");
        Deployment deployment = deploymentBuilder.deploy();
        String deploymentId = deployment.getId();
        log.info("部署ID： [{}]", deploymentId);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        log.info("流程定义文件：[{}] 流程Id: [{}] ", processDefinition.getName(), processDefinition.getId());
        return processDefinition;
    }

    private static ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        ProcessEngine processEngine = cfg.buildProcessEngine();
        String name = processEngine.getName();
        String version = ProcessEngine.VERSION;
        log.info("流程引擎名称：[{}] 版本：[{}] ", name, version);
        return processEngine;
    }

}
