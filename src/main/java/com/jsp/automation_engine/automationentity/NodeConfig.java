package com.jsp.automation_engine.automationentity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sa_wf_nodeconfig")
public class NodeConfig {
    @Id
    @Column(name = "node_id")
    private String NodeId;
    @Column(name = "node_type")
    private String NodeType;
    @Column(name = "node_properties")
    private Map<String,String> NodeProperties;
    @Column(name = "is_start_node")
    private  Boolean isStartNode;
    @Column(name = "is_end_node")
    private Boolean isEndNode;
    @Column(name = "incoming_node")
    private List<NodeConfig> incomingNode;
    @Column(name = "outgoing_node")
    private List<NodeConfig> outgoingNode;
}
