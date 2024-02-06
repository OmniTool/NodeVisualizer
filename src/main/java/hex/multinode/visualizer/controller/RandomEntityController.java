package hex.multinode.visualizer.controller;

import hex.multinode.visualizer.model.dto.NodeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RandomEntityController {

    @GetMapping("/node/random")
    public ResponseEntity<NodeDTO> getRandomNode() {
        Random random = new Random();
        if (random.nextBoolean()) {
            return ResponseEntity.ok(NodeDTO.of("value", Integer.toString(random.nextInt(0, 10))));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
