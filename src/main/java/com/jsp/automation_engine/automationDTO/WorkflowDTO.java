package com.jsp.automation_engine.automationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkflowDTO {
    private String workflowCode;
    private String sourceData;
    private String tenantID;
    private String workflowName;
    private String uniqueField;
    private String entityCode;
}
