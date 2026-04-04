package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.NodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface NodeRepo extends JpaRepository<NodeModel, BigInteger> {
}
