package com.jsp.automation_engine.automationentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NodeExectionResult {
    private Boolean executionResult;
    private String executionStatus;
    private String remarks;
}
