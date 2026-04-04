package com.jsp.automation_engine.automationentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow_model_master")
public class WorkFlowModel extends BaseEntity {
    @Id
    @Column(name = "alt_key")
    private BigInteger altKey;
    @Column(name = "workflow_version")
    private Integer workflowVersion=0;
    @Column(name = "entity_code")
    private String entityCode;
    @Column(name = "workflow_id")
    private String workflowID;
    @Column(name = "tenant_id")
    private String tenantID;
    @Column(name = "workflow_code")
    private String workflowCode;
    @Column(name = "workflow_name")
    private String workflowName;
    @Column(name = "status_flag")
    private String statusFlag="DRAFT";
    @Column(name = "unique_field")
    private String uniqueField;
    @Column(name = "source_data", columnDefinition = "LONGTEXT")
    private String sourceData;
}
