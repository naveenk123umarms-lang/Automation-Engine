package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.NodeExecutionContext;
import com.jsp.automation_engine.automationentity.WorkflowTransactionContext;

public interface NodeExecutionService {
    public void process(WorkflowTransactionContext txContext, NodeExecutionContext executionContext);

}
