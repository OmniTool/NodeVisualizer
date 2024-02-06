package hex.multinode.visualizer.integration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class ProducerCallback implements Callback {

    private static final String UPDATE_DESCRIPTOR = "KafkaSender/updateNode";
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        log.info(UPDATE_DESCRIPTOR + " callback: " + recordMetadata);
        if (e != null) {
            log.error(UPDATE_DESCRIPTOR + " exception: " + e);
        }
    }
}
