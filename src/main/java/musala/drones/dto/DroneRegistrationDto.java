package musala.drones.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
public class DroneRegistrationDto {
    private String serialNumber;
    private DroneModel droneModel;
    private float batteryCapacity;
    private DroneState droneState;


}
