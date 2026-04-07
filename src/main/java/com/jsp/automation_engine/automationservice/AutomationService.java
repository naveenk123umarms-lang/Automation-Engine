package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationDTO.AppResponseDTO;
import com.jsp.automation_engine.automationDTO.UpdateStatusDTO;
import com.jsp.automation_engine.automationDTO.WorkflowDTO;

import java.util.List;

public interface AutomationService {
    AppResponseDTO processSaveUpload(List<WorkflowDTO> dto);

    AppResponseDTO processUpdateStatus(UpdateStatusDTO dto);

}
