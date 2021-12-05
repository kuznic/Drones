package musala.drones.implementation;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.dto.DroneRegistrationResponseDto;
import musala.drones.model.Drone;
import musala.drones.repository.DroneRepository;
import musala.drones.service.DroneService;
import musala.drones.utility.enums.DroneState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    DroneRepository droneRepo;

    @Override
    public BaseResponseDto registerDrone(DroneRegistrationDto drone) {
        var newDrone = new Drone();
        var baseResponse = new BaseResponseDto();
        var registeredDrone = new DroneRegistrationResponseDto();


        BeanUtils.copyProperties(drone, newDrone);

        newDrone.setUid(UUID.randomUUID());
        newDrone.setDroneState(DroneState.IDLE);
        newDrone = droneRepo.save(newDrone);

        registeredDrone.setDroneId(newDrone.getUid());
        registeredDrone.setDroneModel(newDrone.getDroneModel());
        registeredDrone.setDroneState(newDrone.getDroneState());
        registeredDrone.setBatteryCapacity(newDrone.getBatteryCapacity());
        registeredDrone.setLoadedWeight(newDrone.getWeight());
        registeredDrone.setSerialNumber(newDrone.getSerialNumber());
        registeredDrone.setWeightLimit(newDrone.getWeightLimit());
        registeredDrone.setMedications(newDrone.getMedications());

        baseResponse.setData(registeredDrone);
        baseResponse.setCode(HttpStatus.CREATED.value());
        baseResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());


        return baseResponse;
    }
}
