package guru.springframework.services;

import guru.springframework.domain.Beer;
import guru.springframework.mappers.BeerMapper;
import guru.springframework.respositories.BeerRepository;
import guru.springframework.web.controller.NotFoundException;
import guru.springframework.web.model.BeerDto;
import guru.springframework.web.model.BeerPagedList;
import guru.springframework.web.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        beerPage = getBeers(beerName, beerStyle, pageRequest);

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream()
                            .map(beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent().stream()
                            .map(beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(
                            beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                    ),
                    beerPage.getTotalElements());
        }
        return beerPagedList;
    }

    private Page<Beer> getBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        Page<Beer> beerPage;
        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        return beerPage;
    }

    @Override
    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, Boolean showInventoryWithId) {
        BeerDto beerDto;
        if (showInventoryWithId) {
            beerDto = beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        } else {
            beerDto = beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }

        return beerDto;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDtoWithInventory(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDtoWithInventory(beerRepository.save(beer));
    }
}
