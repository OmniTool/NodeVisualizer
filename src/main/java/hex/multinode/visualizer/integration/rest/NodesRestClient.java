package hex.multinode.visualizer.integration.rest;

import hex.multinode.visualizer.model.dto.NodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "nodes", url = "${name.service.url}/api/v1/nodes")
public interface NodesRestClient {

    @PostMapping
    NodeDTO createNode(@RequestHeader String requestId, @RequestBody NodeDTO node);

    @GetMapping("/{id}")
    NodeDTO findById(@RequestHeader String requestId, @RequestParam String id);

    @GetMapping("/find")
    List<NodeDTO> findNodesByTitle(@RequestHeader String requestId, @RequestParam String title);

    @RequestMapping(method = RequestMethod.PATCH)
    NodeDTO update(@RequestHeader String requestId, @RequestBody NodeDTO node);

    @DeleteMapping("/{id}")
    NodeDTO deleteById(@RequestHeader String requestId, @RequestParam String id);

}
