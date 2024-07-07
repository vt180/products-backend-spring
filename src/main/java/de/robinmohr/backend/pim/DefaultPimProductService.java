package de.robinmohr.backend.pim;

import de.robinmohr.backend.pim.infrastructure.kafka.PimProductKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultPimProductService
        implements PimProductService {

    private final PimProductKafkaProducer producer;

    @Override
    public void publish(PimProduct product) {
        producer.sendMessage(product);
    }
}
