package musala.drones.service;

import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;


public interface DroneService {

    BaseResponseDto registerDrone(DroneRegistrationDto drone);

}
