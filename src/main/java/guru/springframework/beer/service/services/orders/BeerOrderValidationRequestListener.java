package guru.springframework.beer.service.services.orders;

import guru.springframework.beer.service.config.JmsConfig;
import guru.springframework.brewery.events.ValidateBeerOrderRequest;
import guru.springframework.brewery.events.ValidateBeerOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationRequestListener {

    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest validateBeerOrderRequest) {

        Message message = MessageBuilder.withPayload(
                                ValidateBeerOrderResult.builder()
                                    .orderId(validateBeerOrderRequest.getBeerOrderDto().getId())
                                    .isValid(beerOrderValidator.validateOrder(validateBeerOrderRequest.getBeerOrderDto()))
                                    .build()
                                )
                                .build();

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, message);
    }
}
