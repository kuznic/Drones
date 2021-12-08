package musala.drones.implementation;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.BatteryLevelDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.dto.DroneRegistrationResponseDto;
import musala.drones.exception.BadRequestException;
import musala.drones.model.Drone;
import musala.drones.repository.DroneRepository;
import musala.drones.service.DroneService;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        if(drone.getBatteryCapacity() > 100 || drone.getBatteryCapacity() < 1)
        {
            throw new BadRequestException("Battery capacity cannot be more than 100 or less than 1");
        }

        if(drone.getWeightLimit() > 500)
        {
            throw new BadRequestException("Weight Limit cannot be more than 500gr");
        }

        switch(drone.getDroneModel().toUpperCase())
        {
            case "LIGHTWEIGHT":
                newDrone.setDroneModel(DroneModel.LIGHTWEIGHT);
                break;

            case "MIDDLEWEIGHT":
                newDrone.setDroneModel(DroneModel.MIDDLEWEIGHT);
                break;
            case "CRUISERWEIGHT":
                newDrone.setDroneModel(DroneModel.CRUISERWEIGHT);
                break;
            case "HEAVYWEIGHT":
                newDrone.setDroneModel(DroneModel.HEAVYWEIGHT);
                break;
            default:
                throw new BadRequestException("Invalid Drone Model specified");

        }


        BeanUtils.copyProperties(drone, newDrone);

        //newDrone.setUid(UUID.randomUUID());
        newDrone.setDroneState(DroneState.IDLE);
        //newDrone.setBatteryCapacity(drone.getBatteryCapacity());
        newDrone = droneRepo.save(newDrone);

        BeanUtils.copyProperties(newDrone, registeredDrone, "id","medications");
        registeredDrone.setDroneId(newDrone.getUid());
        registeredDrone.setBatteryCapacity(newDrone.getBatteryCapacity());

        baseResponse.setData(registeredDrone);
        baseResponse.setCode(HttpStatus.CREATED.value());
        baseResponse.setMessage("Drone has been successfully registered");


        return baseResponse;
    }


    @Override
    public BaseResponseDto getDroneList(Pageable page) {
        return Mono.just(page)
                .subscribeOn(Schedulers.parallel())
                .map(pageable->{
                    var baseResponseDto = new BaseResponseDto();
                    baseResponseDto.setCode(HttpStatus.OK.value());
                    baseResponseDto.setMessage(HttpStatus.OK.getReasonPhrase());
                    baseResponseDto.setData(generateDroneResponseDto(droneRepo.findAll()));
                    return baseResponseDto;
                }).block();
    }

    @Override
    public BaseResponseDto getDrone(UUID droneUid) {
        var drone = droneRepo.findByUid(droneUid);
        var droneResponseDto = new DroneRegistrationResponseDto();
        var baseResponseDto = new BaseResponseDto();

        if(drone == null)
        {
            throw new BadRequestException("Drone with provided Id has not been registered");
        }

        BeanUtils.copyProperties(drone,droneResponseDto, "id");
        droneResponseDto.setDroneId(droneUid);
        droneResponseDto.setLoadedWeight(drone.getWeight());
        droneResponseDto.setBatteryCapacity(drone.getBatteryCapacity());
        baseResponseDto.setData(droneResponseDto);
        baseResponseDto.setCode(HttpStatus.OK.value());
        baseResponseDto.setMessage(HttpStatus.OK.getReasonPhrase());

        return baseResponseDto;
    }

    @Override
    @Transactional
    public BaseResponseDto prepareDroneForLoading(UUID droneUid) {
        var drone = droneRepo.findByUid(droneUid);
        var baseResponseDto = new BaseResponseDto();
        if(drone.getBatteryCapacity() < 25)
        {
            throw new BadRequestException("Battery is too low for drone to be loaded");
        }

        if(drone.getDroneState().equals(DroneState.LOADING))
        {
            throw new BadRequestException("Drone is already in LOADING state");
        }

        if(drone.getWeight() == drone.getWeightLimit())
        {
            throw new BadRequestException("Drone cannot be loaded as the maximum drone weight has been reached");
        }
        drone.setDroneState(DroneState.LOADING);
        droneRepo.save(drone);

        baseResponseDto.setCode(HttpStatus.OK.value());
        baseResponseDto.setMessage("Drone is ready for loading");

        return baseResponseDto;
    }

    @Override
    public BaseResponseDto getAvailableDronesForLoading(Pageable page) {
        return Mono.just(page)
                .subscribeOn(Schedulers.parallel())
                .map(pageable->{
                    var baseResponseDto = new BaseResponseDto();
                    baseResponseDto.setCode(HttpStatus.OK.value());
                    baseResponseDto.setMessage(HttpStatus.OK.getReasonPhrase());
                    baseResponseDto.setData(generateDroneResponseDto(droneRepo.getAllDronesAvailableForLoading(page)));
                    return baseResponseDto;
                }).block();
    }

    @Override
    public BaseResponseDto getDroneBatteryLevel(UUID droneUid)
        {
            var drone = droneRepo.findByUid(droneUid);
            var baseResponseDto = new BaseResponseDto();
            var battLevelDto = new BatteryLevelDto();

            if(drone == null)
            {
                throw new BadRequestException("Drone with provided Id has not been registered");
            }


            battLevelDto.setBatteryLevel(drone.getBatteryCapacity());
            battLevelDto.setDroneID(drone.getId());

            baseResponseDto.setData(battLevelDto);
            baseResponseDto.setCode(HttpStatus.OK.value());
            baseResponseDto.setMessage(HttpStatus.OK.getReasonPhrase());

            return baseResponseDto;
        }

    @Override
    @Scheduled(fixedRate = 60000)
    public void checkDronesBattLevels() {
        List<BatteryLevelDto> battLevels  =  droneRepo.findAll().stream()
                .map(drones-> {
                    var batteryLevel = new BatteryLevelDto();
                    batteryLevel.setBatteryLevel(drones.getBatteryCapacity());
                    batteryLevel.setDroneID(drones.getId());
                    return batteryLevel;
                }).collect(Collectors.toList());
        log.info(battLevels.toString());

    }


    private List<DroneRegistrationResponseDto> generateDroneResponseDto(List<Drone> droneList){

        return droneList.stream()
                .map(drone -> {
                    var droneResponseDto = new DroneRegistrationResponseDto();
                    BeanUtils.copyProperties(drone,droneResponseDto,"id");
                    droneResponseDto.setDroneId(drone.getUid());
                    droneResponseDto.setBatteryCapacity(drone.getBatteryCapacity());
                    droneResponseDto.setLoadedWeight(drone.getWeight());
                    return droneResponseDto;
                }).collect(Collectors.toList());
    }
}
