package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.NodeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeConfigRepo extends JpaRepository<NodeConfig,String> {
}
