package guru.springframework.bootstrap;

import guru.springframework.domain.Beer;
import guru.springframework.respositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Autowired
    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(
                    Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnhnad(12)
                    .upc(337010000001L)
                    .price(new BigDecimal("12.95"))
                    .build()
            );

            beerRepository.save(
                    Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("Pale Ale")
                        .quantityToBrew(200)
                        .minOnhnad(12)
                        .upc(337010000002L)
                        .price(new BigDecimal("11.95"))
                        .build()
            );
        }
    }
}
