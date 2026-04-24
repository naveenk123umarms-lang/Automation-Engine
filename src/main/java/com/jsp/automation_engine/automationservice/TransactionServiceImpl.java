package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.*;
import com.jsp.automation_engine.automationrepository.TransactionLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Service
public class TransactionServiceImpl implements TransationService {
    Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    TransactionLogRepository transactionLogRepository;


    private static final Map<String, Supplier<NodeExecutionService>> nodeMap = new HashMap<>();

    static {
        nodeMap.put("STARTEVENT", StartNode::new);
        nodeMap.put("ENDEVENT", EndNode::new);
    }

    public void createTransactionContext(WorkflowTransactionEntity txEntity, WorkFlowModel workFlowModel) {
        WorkflowTransactionContext workflowTransactionContext = new WorkflowTransactionContext();
        workflowTransactionContext.setWorkflowTransactionEntity(txEntity);
        workflowTransactionContext.setWorkFlowModel(workFlowModel);
        try {
            execute(workflowTransactionContext);
        } catch (Exception e) {
            log.error("Error while executing workflow", e);
            throw e;
        }
    }

    @Override
    public void execute(WorkflowTransactionContext context) {

        WorkFlowModel workFlowModel = context.getWorkFlowModel();
        List<NodeConfig> startNodes = workFlowModel.getStratNodes();
        List<NodeConfig> otherNodes = workFlowModel.getOtherNodes();

        if (context.getCurrentNodeExecutionContextMap() == null) {
            context.setCurrentNodeExecutionContextMap(new HashMap<>());
        }


        if (startNodes != null && !startNodes.isEmpty()) {

            for (NodeConfig node : startNodes) {
                NodeExecutionContext nodeExecutionContext = new NodeExecutionContext();
                nodeExecutionContext.setCurrentNodeConfig(node);
                nodeExecutionContext.setWorkflowTransactionEntity(context.getWorkflowTransactionEntity());

                nodeExecutionContext.setTransactionDataMap(new HashMap<>());

                nodeExecutionContext.setExecutionStatus("STARTED");
                nodeExecutionContext.setWorkFlowModel(context.getWorkFlowModel());
                nodeExecutionContext.setExecutionStart(new Date());
                nodeExecutionContext.setPreviousExecutedNodeConfig(null);
                nodeExecutionContext.setNextExecutedNodeConfig(
                        node.getOutgoingNode() != null ? node.getOutgoingNode() : new java.util.ArrayList<>()
                );
                context.addNodeExecutionContext(node.getNodeId(), nodeExecutionContext);
                executeNode(nodeExecutionContext, context);

            }
        }
        if (otherNodes != null && !otherNodes.isEmpty()) {
            for (NodeConfig node : otherNodes) {
                NodeExecutionContext nodeExecutionContext = new NodeExecutionContext();

                nodeExecutionContext.setCurrentNodeConfig(node);
                nodeExecutionContext.setWorkflowTransactionEntity(context.getWorkflowTransactionEntity());
                nodeExecutionContext.setPreviousExecutedNodeConfig(null);
                nodeExecutionContext.setWorkFlowModel(context.getWorkFlowModel());

                nodeExecutionContext.setTransactionDataMap(new HashMap<>());
                nodeExecutionContext.setExecutionStatus("NOT_STARTED");
                nodeExecutionContext.setNextExecutedNodeConfig(
                        node.getOutgoingNode() != null ? node.getOutgoingNode() : new java.util.ArrayList<>()
                );
                context.getCurrentNodeExecutionContextMap().put(node.getNodeId(), nodeExecutionContext);
                executeNode(nodeExecutionContext, context);
            }
        }
    }

    private void executeNode(NodeExecutionContext nodeExecutionContext, WorkflowTransactionContext workflowTransactionContext) {
        NodeExecutionService node = getNode(nodeExecutionContext.getCurrentNodeConfig().getNodeType());
        uploadTxLog(nodeExecutionContext);
        System.out.println("Executing node: " +
                nodeExecutionContext.getCurrentNodeConfig().getNodeType());
        node.process(workflowTransactionContext, nodeExecutionContext);

    }

    public void uploadTxLog(NodeExecutionContext context) {
        WorkflowTransactionLog workflowTransactionLog = new WorkflowTransactionLog();
        workflowTransactionLog.setAltkey(generateAltKey());
        workflowTransactionLog.setTransactionId(context.getWorkflowTransactionEntity().getTransactionId());
        workflowTransactionLog.setNodeId(context.getCurrentNodeConfig().getNodeId());
        workflowTransactionLog.setNodeType(context.getCurrentNodeConfig().getNodeType());
        workflowTransactionLog.setWorkflowId(context.getWorkFlowModel().getWorkflowID());
        workflowTransactionLog.setTransactionStartDate(new Date());
        workflowTransactionLog.setTransactionEndDate(context.getWorkflowTransactionEntity().getTransactionEndDate());
        workflowTransactionLog.setTransactionUniqueValue(context.getWorkflowTransactionEntity().getTransactionUniqueValue());
        if (context.getPreviousExecutedNodeConfig() != null) {
            workflowTransactionLog.setPreviousNodeId(
                    context.getPreviousExecutedNodeConfig().getNodeId()
            );
        }
        workflowTransactionLog.setStatusFlag("IN_PROGRESS");
        transactionLogRepository.save(workflowTransactionLog);


    }

    public NodeExecutionService getNode(String nodeType) {
//        switch (nodeType){
//            case "STARTEVENT":
//                return new StartNode();
//            case "ENDEVENT":
//                return new EndNode();
//            default:
//                throw new IllegalArgumentException();
//        }
        log.info("Executing node type: {}", nodeType);
        Supplier<NodeExecutionService> serviceSupplier = nodeMap.get(nodeType);
        if (serviceSupplier == null) {
            throw new IllegalArgumentException();
        }

        return serviceSupplier.get();
    }

    public BigInteger generateAltKey() {
        return new BigInteger(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + "");
    }

}
