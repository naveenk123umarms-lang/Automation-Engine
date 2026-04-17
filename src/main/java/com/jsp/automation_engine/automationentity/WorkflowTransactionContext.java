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
public class WorkflowTransactionContext implements Cloneable{
    private WorkFlowModel workFlowModel;
    private WorkflowTransactionEntity workflowTransactionEntity;
    private NodeConfig nextNodeConfig;
    private Map<String,NodeExecutionContext> currentNodeExecutionContextMap;
    private String remarks;
    private Date executionStartDate;
    private Date executionEndDate;

    public Map<String, NodeExecutionContext> getCurrentNodeExecutionContextMap() {
        return currentNodeExecutionContextMap != null
                ? Collections.unmodifiableMap(currentNodeExecutionContextMap)
                : null;
    }

    public void setCurrentNodeExecutionContextMap(Map<String, NodeExecutionContext> map) {

        Map<String, NodeExecutionContext> copy = new HashMap<>();
        for (Map.Entry<String, NodeExecutionContext> entry : map.entrySet()) {
            copy.put(
                    entry.getValue().getCurrentNodeConfig().getNodeId(),
                    entry.getValue() != null ? entry.getValue().clone() : null
            );
        }

        this.currentNodeExecutionContextMap = copy;
    }



    @Override
    public WorkflowTransactionContext clone() {
        try {
            WorkflowTransactionContext clone = (WorkflowTransactionContext) super.clone();
            clone.remarks=this.remarks;
            clone.workflowTransactionEntity=this.workflowTransactionEntity.clone();
            clone.nextNodeConfig = this.nextNodeConfig!=null?this.nextNodeConfig.clone():null;

            if (this.currentNodeExecutionContextMap!=null) {
                HashMap<String, NodeExecutionContext> map = new HashMap<>();
                for (Map.Entry<String, NodeExecutionContext> entry : this.currentNodeExecutionContextMap.entrySet()) {
                    map.put(entry.getKey(), entry.getValue() != null ? entry.getValue().clone() : null);
                }
                clone.currentNodeExecutionContextMap = map;
            }else clone.currentNodeExecutionContextMap=null;

            clone.workFlowModel = this.workFlowModel != null ? this.workFlowModel.clone() : null;
            clone.executionStartDate = this.executionStartDate != null ? (Date) this.executionStartDate.clone() : null;
            clone.executionEndDate = this.executionEndDate != null ? (Date) this.executionEndDate.clone() : null;



            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
