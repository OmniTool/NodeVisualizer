package hex.multinode.visualizer.controller;

import feign.FeignException;
import hex.multinode.visualizer.integration.NodeEndpointManager;
import hex.multinode.visualizer.integration.kafka.NodeKafkaSenderManager;
import hex.multinode.visualizer.model.dto.NodeDTO;
import io.grpc.StatusRuntimeException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/nodes")
@Slf4j
public class NodeController {

    private final NodeEndpointManager<NodeDTO> nodeEndpoint;
    private final NodeKafkaSenderManager nodeSender;

    @Autowired
    public NodeController(NodeEndpointManager nodeEndpoint, NodeKafkaSenderManager nodeSender) {
        this.nodeEndpoint = nodeEndpoint;
        this.nodeSender = nodeSender;
    }

    @GetMapping("/{id}")
    public NodeDTO findNodeById(@PathVariable String id) {
        log.info("findNodeById id = " + id);
        return processNodeWrapException(() -> nodeEndpoint.findById(id));
    }

    @GetMapping("/find")
    public List<NodeDTO> findNodesByTitle(@RequestParam String title) {
        return processNodeWrapException(() -> nodeEndpoint.findByTitle(title));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NodeDTO createNode(@Valid @RequestBody NodeDTO node) {
        return processNodeWrapException(() -> nodeEndpoint.save(node));
    }

    @PatchMapping
    public NodeDTO editNode(@Valid @RequestBody NodeDTO nodeDTO) {
//        return processNodeWrapException(() -> nodeEndpoint.update(nodeDTO));
        nodeSender.updateNode(nodeDTO);
        return findNodeById(nodeDTO.id());
    }

    @DeleteMapping("/{id}")
    public NodeDTO deleteNode(@PathVariable String id) {
        return processNodeWrapException(() -> nodeEndpoint.deleteById(id));
    }

    private <T> T processNodeWrapException(Supplier<T> sup) {
        try {
            return sup.get();
        } catch (FeignException e) {
            throw new ResponseStatusException(e.status(), e.getMessage(), e);
        } catch (StatusRuntimeException e) {
            HttpStatus status = switch (e.getStatus().getCode().name()) {
                case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
                case "INVALID_ARGUMENT" -> HttpStatus.BAD_REQUEST;
                default -> HttpStatus.INTERNAL_SERVER_ERROR;
            };
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }
}
