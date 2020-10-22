package guru.springframework.beer.service.services.inventory;

import guru.springframework.beer.service.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "inventory-failover")
public interface InventoryFailoverServiceFeignClient {

    String INVENTORY_FAILOVER_PATH = "/inventory-failover";

    @RequestMapping(method = RequestMethod.GET, path = INVENTORY_FAILOVER_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory();
}
