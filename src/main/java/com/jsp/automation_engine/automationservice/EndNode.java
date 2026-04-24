package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.NodeExecutionContext;
import com.jsp.automation_engine.automationentity.WorkflowTransactionContext;
import org.springframework.stereotype.Service;

@Service
public class EndNode extends NodeExecutionImpl {
    @Override
    public void process(WorkflowTransactionContext txContext, NodeExecutionContext executionContext) {

    }
}
