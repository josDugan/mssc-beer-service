package guru.springframework.common.events;

import guru.springframework.common.model.BeerDto;
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
