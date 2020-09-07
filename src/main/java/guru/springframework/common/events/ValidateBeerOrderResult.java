package guru.springframework.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateBeerOrderResult implements Serializable {

    private static final long serialVersionUID = -6313840231293036186L;

    private UUID orderId;
    private Boolean isValid;
}
