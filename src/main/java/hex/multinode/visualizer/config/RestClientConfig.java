package hex.multinode.visualizer.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients( basePackages = "hex.multinode.visualizer.integration.rest")
public class RestClientConfig {
}
