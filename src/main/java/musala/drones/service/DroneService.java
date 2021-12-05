package musala.drones.service;

import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import org.springframework.data.domain.Pageable;


public interface DroneService {

    BaseResponseDto registerDrone(DroneRegistrationDto drone);
    BaseResponseDto getDroneList(Pageable page);

}
