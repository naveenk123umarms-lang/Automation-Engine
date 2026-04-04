package com.jsp.automation_engine.controller;

import com.jsp.automation_engine.automationDTO.AppResponseDTO;
import com.jsp.automation_engine.automationDTO.UpdateStatusDTO;
import com.jsp.automation_engine.automationDTO.WorkflowDTO;
import com.jsp.automation_engine.automationservice.AutomationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutomationController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AutomationController.class);
    @Autowired
            AutomationServiceImpl service;

    @PostMapping(value = "/saveUpload")
    public AppResponseDTO saveUpload(@RequestBody List<WorkflowDTO> dto) {
        return service.processSaveUpload(dto);
    }
    @PostMapping(value = "/updateStatus")
    public AppResponseDTO updateStatus(@RequestBody UpdateStatusDTO dto){
        return service.processUpdateStatus(dto);
    }
}
