package musala.drones.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Data
@Slf4j
@Component
public class BatteryLevelDto{
    private int batteryLevel;


}
