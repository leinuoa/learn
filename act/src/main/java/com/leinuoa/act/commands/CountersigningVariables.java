package com.leinuoa.act.commands;

import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;

/**
 *  Activiti 会签任务中变量标志
 *
 * {@link MultiInstanceActivityBehavior}
 */
public interface CountersigningVariables {

    /**
     *  默认审核人
     */
    String ASSIGNEE_USER = "user";

    /**
     *  审核人集合
     */
    String ASSIGNEE_LIST = "userList";

    /**
     *  会签任务总数
     */
    String NUMBER_OF_INSTANCES = "nrOfInstances";

    /**
     *  正在执行的会签总数
     */
    String NUMBER_OF_ACTIVE_INSTANCES = "nrOfActiveInstances";

    /**
     *  已完成的会签任务总数
     */
    String NUMBER_OF_COMPLETED_INSTANCES = "nrOfCompletedInstances";

    /**
     *  会签任务表示
     *  collectionElementIndexVariable
     */
    String LOOP_COUNTER = "loopCounter";
}
