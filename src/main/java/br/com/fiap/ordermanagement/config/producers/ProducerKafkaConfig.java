package br.com.fiap.ordermanagement.config.producers;

import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderHistoryResponseDto;
import br.com.fiap.ordermanagement.models.dtos.responses.GetOrderReponseDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration

public class ProducerKafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * Producer factory
     * @return
     */
    @Bean
    public ProducerFactory<String, GetOrderHistoryResponseDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        // Set the producer configuration properties
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        // Set the key serializer class
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Set the value serializer class
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Return the producer factory
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Kafka template
     * @return
     */
    @Bean
    public KafkaTemplate<String, GetOrderHistoryResponseDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
