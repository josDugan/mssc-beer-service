package guru.springframework.beer.service.services.brewing;

import guru.springframework.beer.service.config.JmsConfig;
import guru.springframework.beer.service.domain.Beer;
import guru.springframework.brewery.events.BrewBeerEvent;
import guru.springframework.brewery.events.NewInventoryEvent;
import guru.springframework.beer.service.respositories.BeerRepository;
import guru.springframework.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Optional<Beer> beerOptional = beerRepository.findById(beerDto.getId());

        beerOptional.ifPresentOrElse(beer -> {
            beerDto.setQuantityOnHand(beer.getQuantityToBrew());
            log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());
        }, () -> log.debug("Beer not found for id: " + beerDto.getId()));

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
