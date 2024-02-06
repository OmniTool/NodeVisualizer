package hex.multinode.visualizer.integration;

import java.util.List;

public interface NodeEndpointManager<ND> {
    ND findById(String id);

    List<ND> findByTitle(String title);

    ND save(ND node);

    ND update(ND node);

    ND deleteById(String id);
}
