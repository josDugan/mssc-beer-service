package guru.springframework.events;


import guru.springframework.web.model.BeerDto;

public class BrewBeerEvent  extends BeerEvent {
    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
