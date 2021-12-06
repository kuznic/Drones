package musala.drones.service;

import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface DroneService {

    BaseResponseDto registerDrone(DroneRegistrationDto drone);
    BaseResponseDto getDroneList(Pageable page);
    BaseResponseDto getDrone(UUID droneUid);
    BaseResponseDto prepareDroneForLoading(UUID droneUid);
    BaseResponseDto getAvailableDronesForLoading(Pageable page);
    BaseResponseDto getDroneBatteryLevel(UUID droneUid);

}
