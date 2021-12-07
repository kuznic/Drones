package musala.drones.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Data
@Slf4j
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(value)
public class DroneRegistrationResponseDto {
    private UUID droneId;
    private String serialNumber;
    private DroneModel droneModel;
    private int weightLimit;
    private float batteryCapacity;
    private DroneState droneState;
    private int loadedWeight;


}
