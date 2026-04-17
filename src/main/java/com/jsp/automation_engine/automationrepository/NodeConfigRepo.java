package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.NodeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeConfigRepo extends JpaRepository<NodeConfig,String> {
    public NodeConfig findByNodeIdAndIsStartNodeTrue(String nodeId);

    public List<NodeConfig> findByIsStartNodeFalse();
}
