package hex.multinode.visualizer.integration.kafka;

import hex.multinode.visualizer.model.dto.NodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class NodeKafkaSenderManager {

    @Value("${spring.kafka.consumer.topic}")
    private String kafkaTopic;

    private KafkaTemplate<String, NodeDTO> kafkaTemplate;

    private ProducerFactory<String, NodeDTO> producerFactory;

    @Autowired
    public NodeKafkaSenderManager(KafkaTemplate<String, NodeDTO> kafkaTemplate, ProducerFactory<String, NodeDTO> producerFactory) {
        this.kafkaTemplate = kafkaTemplate;
        this.producerFactory = producerFactory;
    }

    public void updateNode(NodeDTO data) {
        //TODO партиционирование по ключу по юзерам
        var record = new ProducerRecord<String, NodeDTO>(kafkaTopic, data);
        record.headers().add("operation","updateNode".getBytes(StandardCharsets.UTF_8));
        try (var producer = producerFactory.createProducer()) {
            producer.send(record, new ProducerCallback()); //асинхронный вызов с коллбэком
        }
    }
}
