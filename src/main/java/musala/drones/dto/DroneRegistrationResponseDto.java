package musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.model.Medication;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Data
@Slf4j
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneRegistrationResponseDto {
    private UUID droneId;
    private String serialNumber;
    private DroneModel droneModel;
    private int weightLimit;
    private float batteryCapacity;
    private DroneState droneState;
    private int loadedWeight;
    List<Medication> medications;


}