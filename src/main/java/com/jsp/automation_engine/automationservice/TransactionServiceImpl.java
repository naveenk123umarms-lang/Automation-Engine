package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.*;
import com.jsp.automation_engine.automationrepository.AutomationEngineRepo;
import com.jsp.automation_engine.automationrepository.NodeConfigRepo;
import com.jsp.automation_engine.automationrepository.NodeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionServiceImpl implements TransationService{
    @Autowired
    AutomationServiceImpl automationService;
    @Autowired
    AutomationEngineRepo automationEngineRepo;
    @Autowired
    NodeRepo nodeRepo;
    @Autowired
    NodeConfigRepo nodeConfigRepo;


    @Override
    public void execute(WorkflowTransactionContext context) {
//        List<NodeModel> nodeModels = nodeRepo.findByWorkflowID(workFlowModel.getWorkflowID());
//        NodeConfig startNode=null;
//        for (NodeModel node:nodeModels) {
//             startNode = nodeConfigRepo.findByNodeIdAndIsStartNodeTrue(node.getNodeID());
//        }
        WorkFlowModel workFlowModel = context.getWorkFlowModel();
        List<NodeConfig> startNodes = workFlowModel.getStratNodes();
        List<NodeConfig> otherNodes = workFlowModel.getOtherNodes();

        if(startNodes!=null&& !startNodes.isEmpty()) {
            if (context.getCurrentNodeExecutionContextMap() == null) {
                context.setCurrentNodeExecutionContextMap(new HashMap<>());
            }
            for (NodeConfig node : startNodes) {
                NodeExecutionContext nodeExecutionContext = new NodeExecutionContext();
                nodeExecutionContext.setCurrentNodeConfig(node);
                nodeExecutionContext.setWorkflowTransactionEntity(context.getWorkflowTransactionEntity());
                nodeExecutionContext.setTransactionDataMap(new HashMap<>());
                nodeExecutionContext.setExecutionStatus("STARTED");
                nodeExecutionContext.setPreviousExecutedNodeConfig(null);
                nodeExecutionContext.setNextExecutedNodeConfig(node.getOutgoingNode());
                context.getCurrentNodeExecutionContextMap().put(node.getNodeId(),nodeExecutionContext);
                //executenode(nodeExecutionContext);
            }
        }
        for (NodeConfig node:otherNodes){
            NodeExecutionContext nodeExecutionContext=new NodeExecutionContext();
            List<NodeConfig> incomingNode = node.getIncomingNode();
            if(incomingNode!=null){
                nodeExecutionContext.setPreviousExecutedNodeConfig(incomingNode.get(0));
            }

            nodeExecutionContext.setCurrentNodeConfig(node);
            nodeExecutionContext.setWorkflowTransactionEntity(context.getWorkflowTransactionEntity());
            nodeExecutionContext.setTransactionDataMap(new HashMap<>());
            nodeExecutionContext.setExecutionStatus("NOT_STARTED");
            nodeExecutionContext.setNextExecutedNodeConfig(node.getOutgoingNode());
            context.getCurrentNodeExecutionContextMap().put(node.getNodeId(),nodeExecutionContext);
            //executenode();

        }


    }

}
