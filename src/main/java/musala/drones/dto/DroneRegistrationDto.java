package musala.drones.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Slf4j
@Component
public class DroneRegistrationDto {
    @Size(max = 100,message="The Serial number cannot be more than 100 characters")
    private String serialNumber;
    private String droneModel;
    private int batteryCapacity;
    @Positive(message = "Weight limit should be positive")
    private int weightLimit;


}
