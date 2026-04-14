package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationDTO.AppResponseDTO;
import com.jsp.automation_engine.automationDTO.UpdateStatusDTO;
import com.jsp.automation_engine.automationDTO.WorkflowDTO;
import com.jsp.automation_engine.automationentity.NodeConfig;
import com.jsp.automation_engine.automationentity.NodeModel;
import com.jsp.automation_engine.automationentity.WorkFlowModel;
import com.jsp.automation_engine.automationrepository.AutomationEngineRepo;
import com.jsp.automation_engine.automationrepository.NodeConfigRepo;
import com.jsp.automation_engine.automationrepository.NodeRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AutomationServiceImpl implements AutomationService {
    @Autowired
    AutomationEngineRepo repo;
    Logger logger = LoggerFactory.getLogger(AutomationServiceImpl.class);
    @Autowired
    NodeRepo nodeRepo;
    @Autowired
    NodeConfigBuilder nodeConfigBuilder;
    @Autowired
    NodeConfigRepo nodeConfigRepo;


    @Override
    public AppResponseDTO processSaveUpload(List<WorkflowDTO> dto) {
        try {

            List<WorkFlowModel> collect = dto.stream().map(modeldata -> {

                Optional<WorkFlowModel> exist = repo.findByWorkflowCodeAndStatusFlag(modeldata.getWorkflowCode(), "DRAFT");
                WorkFlowModel workFlowModel;
                if (exist.isPresent()) {
                    workFlowModel = exist.get();
                    workFlowModel.setSourceData(modeldata.getSourceData());
                    workFlowModel.setTenantID(modeldata.getTenantID());
                    workFlowModel.setWorkflowName(modeldata.getWorkflowName());
                    workFlowModel.setUniqueField(modeldata.getUniqueField());
                    workFlowModel.setEntityCode(modeldata.getEntityCode());
                } else {

                    workFlowModel = new WorkFlowModel();
                    workFlowModel.setAltKey(generateAltKey());
                    workFlowModel.setWorkflowCode(modeldata.getWorkflowCode());
                    workFlowModel.setSourceData(modeldata.getSourceData());
                    workFlowModel.setTenantID(modeldata.getTenantID());
                    workFlowModel.setWorkflowName(modeldata.getWorkflowName());
                    workFlowModel.setUniqueField(modeldata.getUniqueField());
                    workFlowModel.setEntityCode(modeldata.getEntityCode());
                    workFlowModel.setWorkflowID(modeldata.getWorkflowCode() + "_" + workFlowModel.getWorkflowVersion());
                }
                return workFlowModel;
            }).collect(Collectors.toList());


            return new AppResponseDTO("200", null, "Sucess", repo.saveAll(collect));
        } catch (Exception e) {
            return new AppResponseDTO("500", e.getMessage(), "failure", null);
        }
    }

    @Transactional
    @Override
    public AppResponseDTO processUpdateStatus(UpdateStatusDTO dto) {
        try {
            WorkFlowModel active = repo.findWithLock(dto.getWorkflowCode(), "ACTIVE");
            if (active != null) {
                active.setStatusFlag("INACTIVE");
                repo.save(active);
            }
            WorkFlowModel draft = repo.findWithLock(dto.getWorkflowCode(), "DRAFT");
            draft.setStatusFlag("ACTIVE");
            BigInteger bigInteger = repo.maxVersion(dto.getWorkflowCode());
            logger.debug(" maxVersion:{}",bigInteger);
            draft.setWorkflowVersion(Integer.valueOf(String.valueOf(bigInteger))+1);
            draft.setWorkflowID(draft.getWorkflowCode() + "_" + draft.getWorkflowVersion());
            repo.save(draft);

            List<NodeModel> nodeList = parse(draft.getSourceData(), draft.getWorkflowID(),draft.getTenantID());
            nodeRepo.saveAll(nodeList);

            WorkFlowModel flowModel = getWorkflowbyIdandtId(draft.getWorkflowID(), draft.getTenantID());
            List<NodeConfig> nodeConfig = nodeConfigBuilder.getNodeConfig(flowModel.getNodeProperties());
            nodeConfigRepo.saveAll(nodeConfig);


            return new AppResponseDTO("200", null, "success", null);
        } catch (Exception e) {
            return new AppResponseDTO("500", e.getMessage(), "failure", null);
        }

    }
    public WorkFlowModel getWorkflowbyIdandtId(String wfId, String tID) {
        WorkFlowModel byWorkflowIDAndTenantID = repo.findByWorkflowIDAndTenantID(wfId, tID);
        List<NodeModel> nodeModelList = nodeRepo.findByWorkflowIDAndTenantID(wfId, tID);
        byWorkflowIDAndTenantID.setNodeProperties(nodeModelList);
        return byWorkflowIDAndTenantID;
    }
    public BigInteger generateAltKey() {
        return new BigInteger(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + "");
    }
    public List<NodeModel> parse(String xml, String workflowCode, String tenantId) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            BPMNHandler handler = new BPMNHandler(workflowCode, tenantId);

            saxParser.parse(new InputSource(new StringReader(xml)), handler);

            return handler.getNodes();

        } catch (Exception e) {
            throw new RuntimeException("Error parsing BPMN XML", e);
        }
    }

    }



