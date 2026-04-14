package com.jsp.automation_engine.automationentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeExecutionContext implements Cloneable {
    private String executionStatus;
    private NodeConfig currentNodeConfig;
    private NodeConfig previousExecutedNodeConfig;
    private List<NodeConfig> nextExecutedNodeConfig;
    private Date executionStart;
    private Date executionEnd;
    private WorkFlowModel workFlowModel;
    private WorkflowTransactionEntity workflowTransactionEntity;
    private Map<String,Object> transactionDataMap;
    private NodeExectionResult nodeExectionResult;

    @Override
    public NodeExecutionContext clone() {
        try {
            NodeExecutionContext clone = (NodeExecutionContext) super.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
