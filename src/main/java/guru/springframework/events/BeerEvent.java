package guru.springframework.events;

import guru.springframework.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 3150115053345909767L;

    private final BeerDto beerDto;
}
