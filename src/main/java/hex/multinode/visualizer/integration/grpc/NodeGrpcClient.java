package hex.multinode.visualizer.integration.grpc;

import com.example.hex.integration.grpc.*;
import hex.multinode.visualizer.model.dto.NodeDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@Slf4j
public class NodeGrpcClient {

    @Value("${grpc.server.target}")
    private String target;

    public NodeDTO createNode(NodeDTO node) {
        CreateNodeRequest request = CreateNodeRequest.newBuilder()
                .setTitle(node.title())
                .setText(node.contentText())
                .build();
        CreateNodeResponse response = sendRequest(request, (stub) -> stub.createNode(request));
        return convertNodeProtoToDTO(response.getNode());
    }

    public NodeDTO findNodeById(String id) {
        FindNodeByIdRequest request = FindNodeByIdRequest.newBuilder()
                .setId(id)
                .build();
        FindNodeByIdResponse response = sendRequest(request, (stub) -> stub.findNodeById(request));
        return convertNodeProtoToDTO(response.getNode());
    }

    public List<NodeDTO> findNodesByTitle(String title) {
        FindNodesByTitleRequest request = FindNodesByTitleRequest.newBuilder()
                .setTitle(title)
                .build();
        FindNodesByTitleResponse response = sendRequest(request, (stub) -> stub.findNodesByTitle(request));
        return convertNodesProtoToDTO(response.getNodeList());
    }

    public NodeDTO deleteNodeById(String id) {
        DeleteNodeByIdRequest request = DeleteNodeByIdRequest.newBuilder()
                .setId(id)
                .build();
        DeleteNodeByIdResponse response = sendRequest(request, (stub) -> stub.deleteNodeById(request));
        return convertNodeProtoToDTO(response.getNode());
    }

    private <Rq, Rs> Rs sendRequest(Rq request, Function<NodeEndpointServiceGrpc.NodeEndpointServiceBlockingStub, Rs> func) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        NodeEndpointServiceGrpc.NodeEndpointServiceBlockingStub stub = NodeEndpointServiceGrpc.newBlockingStub(channel);
        log.info("gRPC request: " + request);
        Rs response = func.apply(stub);
        log.info("gRPC response: " + response);
        channel.shutdownNow();
        return response;
    }

    private List<NodeDTO> convertNodesProtoToDTO(List<NodeProto> nodes) {
        return nodes.stream().map(NodeGrpcClient::convertNodeProtoToDTO).toList();
    }

    private static NodeDTO convertNodeProtoToDTO(NodeProto resultNode) {
        return NodeDTO.of(resultNode.getId(), resultNode.getTitle(), resultNode.getText());
    }
}
