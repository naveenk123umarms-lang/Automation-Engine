package com.jsp.automation_engine.automationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusDTO {
    private String workflowCode;
    private String workflowID;
    private String statusFlag;
}
