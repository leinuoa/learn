package com.leinuoa.act;


import com.leinuoa.act.commands.AddMultiInstanceExecutionCmd;
import com.leinuoa.act.commands.DeleteMultiInstanceExecutionCmd;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActApplicationTests {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署流程
     */
    @Test
    public void deployment() {
        repositoryService.createDeployment().addClasspathResource("diagrams/multipleInstance.bpmn20.xml").deploy();
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcessInstance(){
        String key ="multiAssigneeProcess";
//        String[] users = {"userA","userB","userC"};
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("userA");
        assigneeList.add("userB");
        assigneeList.add("userC");
        HashMap<String,Object> variables = new HashMap<>();
//        variables.put("userList", Arrays.asList(users));
        variables.put("userList", assigneeList);
        runtimeService.startProcessInstanceByKey(key,variables);
    }

    /**
     *  测试会签减签
     */
    @Test
    public void testRemoveCountersigningTask() {

        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("userC");
        assigneeList.add("userF");

        processEngine.getManagementService().executeCommand(new DeleteMultiInstanceExecutionCmd("5014", assigneeList));
    }

    /**
     *  测试会签加签
     */
    @Test
    public void testAddCountersigningTask() {

        List<String> assigneeList = new ArrayList<>();

        assigneeList.add("userC");
        assigneeList.add("userF");

        processEngine.getManagementService().executeCommand(new AddMultiInstanceExecutionCmd("5014", assigneeList));
    }

    /**
     * 完成user1的task任务
     */
    @Test
    public void completeTask(){
        String taskId = "5014";
        taskService.complete(taskId);
    }

}
