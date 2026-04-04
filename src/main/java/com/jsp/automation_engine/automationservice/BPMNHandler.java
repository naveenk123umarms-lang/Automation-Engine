package com.jsp.automation_engine.automationservice;

import com.jsp.automation_engine.automationentity.NodeModel;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BPMNHandler extends DefaultHandler {

    private List<NodeModel> nodes = new ArrayList<>();
    private Map<String, NodeModel> nodeMap = new HashMap<>();
    private List<String[]> sequenceFlows = new ArrayList<>();

    private NodeModel currentNode;

    private String workflowCode;
    private String tenantId;

    public BPMNHandler(String workflowCode, String tenantId) {
        this.workflowCode = workflowCode;
        this.tenantId = tenantId;
    }

    public BPMNHandler() {

    }

    public List<NodeModel> getNodes() {
        return nodes;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        switch (qName) {

            case "bpmn:startEvent":
            case "bpmn:endEvent":
            case "bpmn:task":

                currentNode = new NodeModel();
                currentNode.setWorkflowID(workflowCode);
                currentNode.setTenantID(tenantId);

                currentNode.setNode_ID(attributes.getValue("id"));
                currentNode.setNodeType(qName.replace("bpmn:", "").toUpperCase());

                currentNode.setNodeProperties(new HashMap<>());
                currentNode.setIncomingNodes(new ArrayList<>());
                currentNode.setOutgoingNodes(new ArrayList<>());

                if (attributes.getValue("name") != null) {
                    currentNode.getNodeProperties().put("name", attributes.getValue("name"));
                }

                break;

            case "bpmn:sequenceFlow":

                sequenceFlows.add(new String[]{
                        attributes.getValue("sourceRef"),
                        attributes.getValue("targetRef")
                });
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (qName.equals("bpmn:startEvent") ||
                qName.equals("bpmn:endEvent") ||
                qName.equals("bpmn:task")) {

            Map<String, String> props = new HashMap<>();

            props.put("name", currentNode.getNodeProperties().getOrDefault("name", ""));
            props.put("type", currentNode.getNodeType());

            props.put("incoming", String.join(",", currentNode.getIncomingNodes()));
            props.put("outgoing", String.join(",", currentNode.getOutgoingNodes()));

            if ("TASK".equals(currentNode.getNodeType())) {
                props.put("actionType", "SEND_EMAIL");
            }

            currentNode.setNodeProperties(props);

            nodes.add(currentNode);
            nodeMap.put(currentNode.getNode_ID(), currentNode);

            currentNode = null;
        }
    }

    @Override
    public void endDocument() {

        // Step 1: Resolve flows
        for (String[] flow : sequenceFlows) {
            String source = flow[0];
            String target = flow[1];

            NodeModel sourceNode = nodeMap.get(source);
            NodeModel targetNode = nodeMap.get(target);

            if (sourceNode != null) {
                sourceNode.getOutgoingNodes().add(target);
            }

            if (targetNode != null) {
                targetNode.getIncomingNodes().add(source);
            }
        }

        // Step 2: Build properties AFTER connections are ready
        for (NodeModel node : nodes) {

            Map<String, String> props = new HashMap<>();

            props.put("name", node.getNodeProperties().getOrDefault("name", ""));
            props.put("type", node.getNodeType());

            props.put("incoming", String.join(",", node.getIncomingNodes()));
            props.put("outgoing", String.join(",", node.getOutgoingNodes()));

            if ("TASK".equals(node.getNodeType())) {
                props.put("actionType", "SEND_EMAIL");
            }

            node.setNodeProperties(props);
        }
    }
}