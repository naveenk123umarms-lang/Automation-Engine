package com.jsp.automation_engine.automationentity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NodeExectionResult implements Cloneable{
    private Boolean executionResult;
    private String executionStatus;
    private String remarks;

    @Override
    public NodeExectionResult clone() {
        try {
            NodeExectionResult clone = (NodeExectionResult) super.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
