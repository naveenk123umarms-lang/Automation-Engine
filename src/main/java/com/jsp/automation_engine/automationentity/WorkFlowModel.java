package com.jsp.automation_engine.automationentity;

import com.jsp.automation_engine.automationservice.NodeConfigBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workflow_model_master")
public class WorkFlowModel extends BaseEntity implements Cloneable {
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
    @Transient
    private List<NodeModel> nodeProperties;

    @Override
    public WorkFlowModel clone() {
        try {
            WorkFlowModel clone = (WorkFlowModel) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public List<NodeConfig> getStratNodes() {
        return new NodeConfigBuilder().getNodeConfig(this.nodeProperties).stream().filter(each->each.getNodeType().equals("STARTEVENT"))
                .collect(Collectors.toList());
    }
    public List<NodeConfig> getOtherNodes(){
        return new NodeConfigBuilder().getNodeConfig(this.nodeProperties).stream().filter(each->!each.getNodeType().equals("STARTEVENT"))
                .collect(Collectors.toList());
    }
}
