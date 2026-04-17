package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.WorkflowTransactionContext;

public interface TransationService {
    void execute(WorkflowTransactionContext context);
}
