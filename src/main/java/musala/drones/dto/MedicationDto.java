package musala.drones.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Slf4j
@Component
public class MedicationDto {
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$",message = "Only letters, numbers, underscore and dash are allowed")
    private String name;
    private int weight;
    @Pattern(regexp = "^[A-Z0-9_]*$",message = "Only upper case letters, underscore and numbers are allowed")
    private String code;
    private UUID droneId;
    private byte[] image;
}
