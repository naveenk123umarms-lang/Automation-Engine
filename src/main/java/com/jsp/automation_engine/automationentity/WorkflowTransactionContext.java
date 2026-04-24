package com.jsp.automation_engine.automationentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Collections;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkflowTransactionContext implements Cloneable {
    private WorkFlowModel workFlowModel;
    private WorkflowTransactionEntity workflowTransactionEntity;
    private NodeConfig nextNodeConfig;
    private Map<String, NodeExecutionContext> currentNodeExecutionContextMap;
    private String remarks;
    private Date executionStartDate;
    private Date executionEndDate;

    public void addNodeExecutionContext(String nodeId, NodeExecutionContext ctx) {
        if (this.currentNodeExecutionContextMap == null) {
            this.currentNodeExecutionContextMap = new HashMap<>();
        }
        this.currentNodeExecutionContextMap.put(nodeId, ctx);
    }

    public void setCurrentNodeExecutionContextMap(Map<String, NodeExecutionContext> map) {
        if (map == null) {
            this.currentNodeExecutionContextMap = null;
            return;
        }

        Map<String, NodeExecutionContext> copy = new HashMap<>();
        for (Map.Entry<String, NodeExecutionContext> entry : map.entrySet()) {
            copy.put(entry.getKey(),
                    entry.getValue() != null ? entry.getValue().clone() : null);
        }

        this.currentNodeExecutionContextMap = copy;
    }

    @Override
    public WorkflowTransactionContext clone() {
        try {
            WorkflowTransactionContext clone = (WorkflowTransactionContext) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
