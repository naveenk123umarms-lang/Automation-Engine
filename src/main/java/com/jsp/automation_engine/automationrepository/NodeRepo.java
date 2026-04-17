package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.NodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NodeRepo extends JpaRepository<NodeModel, BigInteger> {
    public List<NodeModel> findByWorkflowIDAndTenantID(String wfId,String tId);
    public List<NodeModel> findByWorkflowID(String wfId);
}
