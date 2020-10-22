package guru.springframework.beer.service.services.inventory;

import guru.springframework.beer.service.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class InventoryFailoverServiceFeignClientImpl implements InventoryServiceFeignClient {

    private final InventoryFailoverServiceFeignClient inventoryFailoverServiceFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(UUID beerId) {
        log.debug("In failover feign client");
        return inventoryFailoverServiceFeignClient.getOnhandInventory();
    }
}
