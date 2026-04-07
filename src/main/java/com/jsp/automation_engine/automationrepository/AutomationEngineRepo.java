package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.WorkFlowModel;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AutomationEngineRepo extends JpaRepository<WorkFlowModel, BigInteger> {

    public Optional<WorkFlowModel> findByWorkflowCodeAndStatusFlag(String wf, String status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM WorkFlowModel w WHERE w.workflowCode = :code AND w.statusFlag = :status")
    WorkFlowModel findWithLock(String code, String status);

    @Query(value = "SELECT MAX(workflow_version) FROM workflow_model_master\n" +
            " WHERE workflow_code=:workflowCode;",nativeQuery = true)
    public BigInteger maxVersion(String workflowCode);

    public WorkFlowModel findByWorkflowIDAndTenantID(String wfId,String tId);
}
