package com.jsp.automation_engine.automationrepository;

import com.jsp.automation_engine.automationentity.WorkflowTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface TransactionRepository extends JpaRepository<WorkflowTransactionEntity, BigInteger> {
}
