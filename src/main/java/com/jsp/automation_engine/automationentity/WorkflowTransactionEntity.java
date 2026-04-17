package com.jsp.automation_engine.automationentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sa_wf_transaction")
public class WorkflowTransactionEntity extends BaseEntity implements Cloneable {
    @Id
    @Column(name = "alt_key")
    private BigInteger altkey;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "transaction_unique_value")
    private String transactionUniqueValue;
    @Column(name = "workflow_id")
    private String workflowId;
    @Column(name = "workflow_code")
    private String workflowCode;
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "status_flag")
    private String statusFlag;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "transaction_start_date")
    private Date transactionStartDate;
    @Column(name = "transaction_end_date")
    private  Date transactionEndDate;


    @Override
    public WorkflowTransactionEntity clone() {
        try {
            WorkflowTransactionEntity clone = (WorkflowTransactionEntity) super.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
