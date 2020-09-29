package guru.springframework.brewery.events;

import guru.springframework.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 3150115053345909767L;

    private BeerDto beerDto;
}
