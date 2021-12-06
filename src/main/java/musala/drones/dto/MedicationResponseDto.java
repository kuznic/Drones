package musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.model.Drone;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Slf4j
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicationResponseDto {
    private UUID medicationId;
    private String name;
    private int weight;
    private String code;
    private UUID droneId;
    private byte[] image;
}
