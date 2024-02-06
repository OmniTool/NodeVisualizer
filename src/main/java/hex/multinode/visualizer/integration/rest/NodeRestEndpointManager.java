package hex.multinode.visualizer.integration.rest;

import hex.multinode.visualizer.integration.NodeEndpointManager;
import hex.multinode.visualizer.model.dto.NodeDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

//@Service
public class NodeRestEndpointManager implements NodeEndpointManager<NodeDTO> {

    private final NodesRestClient nodesClient;

    @Autowired
    public NodeRestEndpointManager(NodesRestClient nodesClient) {
        this.nodesClient = nodesClient;
    }

    @Override
    public NodeDTO findById(String id) {
        return nodesClient.findById(generateRqId(), id);
    }

    @Override
    public List<NodeDTO> findByTitle(String title) {
        return nodesClient.findNodesByTitle(generateRqId(), title);
    }

    @Override
    public NodeDTO save(NodeDTO node) {
        return nodesClient.createNode(generateRqId(), node);
    }

    @Override
    public NodeDTO update(NodeDTO node) {
        return nodesClient.update(generateRqId(), node);
    }

    @Override
    public NodeDTO deleteById(String id) {
        return nodesClient.deleteById(generateRqId(), id);
    }

    static String generateRqId() {
        return UUID.randomUUID().toString();
    }
}
