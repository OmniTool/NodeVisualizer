package hex.multinode.visualizer.integration.grpc;

import hex.multinode.visualizer.integration.NodeEndpointManager;
import hex.multinode.visualizer.model.dto.NodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeGrpcEndpointManager implements NodeEndpointManager<NodeDTO> {

    private final NodeGrpcClient nodesClient;

    @Autowired
    public NodeGrpcEndpointManager(NodeGrpcClient nodesClient) {
        this.nodesClient = nodesClient;
    }

    @Override
    public NodeDTO findById(String id) {
        return nodesClient.findNodeById(id);
    }

    @Override
    public List<NodeDTO> findByTitle(String title) {
        return nodesClient.findNodesByTitle(title);
    }

    @Override
    public NodeDTO save(NodeDTO node) {
        return nodesClient.createNode(node);
    }

    @Override
    public NodeDTO update(NodeDTO node) {
        throw new UnsupportedOperationException("Update Node by Grpc - is not allowed");
    }

    @Override
    public NodeDTO deleteById(String id) {
        return nodesClient.deleteNodeById(id);
    }
}
