package guru.springframework.beer.service.bootstrap;

import guru.springframework.beer.service.domain.Beer;
import guru.springframework.beer.service.respositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "01234567890";
    public static final String BEER_2_UPC = "01234567891";
    public static final String BEER_3_UPC = "01234567892";
    public static final UUID BEER_1_UUID = UUID.fromString("e7bd7a37-d392-4074-8ca2-c80ba7f9e5ee");
    public static final UUID BEER_2_UUID = UUID.fromString("e7bd7a37-d392-4074-8ca2-c80ba7f9e5ee");
    public static final UUID BEER_3_UUID = UUID.fromString("e7bd7a37-d392-4074-8ca2-c80ba7f9e5ee");

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
                    .minOnHand(12)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build()
            );

            beerRepository.save(
                    Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("PALE_ALE")
                        .quantityToBrew(200)
                        .minOnHand(12)
                        .upc(BEER_2_UPC)
                        .price(new BigDecimal("11.95"))
                        .build()
            );

            beerRepository.save(
                    Beer.builder()
                            .beerName("No Hammers On The Bar")
                            .beerStyle("PALE_ALE")
                            .quantityToBrew(200)
                            .minOnHand(12)
                            .upc(BEER_3_UPC)
                            .price(new BigDecimal("11.95"))
                            .build()
            );
        }
    }
}
