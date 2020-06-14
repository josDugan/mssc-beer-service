package guru.springframework.services;

import guru.springframework.web.model.BeerDto;
import guru.springframework.web.model.BeerPagedList;
import guru.springframework.web.model.BeerStyle;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest);

    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
