package musala.drones.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import musala.drones.dto.*;
import musala.drones.exception.BadRequestException;
import musala.drones.model.Drone;
import musala.drones.model.Medication;
import musala.drones.repository.DroneRepository;
import musala.drones.repository.MedicationRepository;
import musala.drones.service.DroneService;
import musala.drones.service.MedicationService;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    MedicationRepository medicationRepo;

    @Autowired
    DroneRepository droneRepo;


    @Override
    @Transactional
    public BaseResponseDto addMedication(MedicationDto medicationDto) {
        var medication = new Medication();
        var baseResponse = new BaseResponseDto();
        var medicationResponseDto = new MedicationResponseDto();
        var drone = droneRepo.findByUid(medicationDto.getDroneId());

        if(drone == null)
        {
            throw new BadRequestException("Drone does not exist");
        }

        if(!drone.getDroneState().equals(DroneState.LOADING))
        {
            throw new BadRequestException("The specified drone is not available for loading");
        }

        if(medicationDto.getWeight() > drone.getWeightLimit() || medication.getWeight() + drone.getWeight() > drone.getWeightLimit())
        {
            throw new BadRequestException("Maximum weight capacity for the drone has been reached");
        }

        drone.setWeight(drone.getWeight() + medicationDto.getWeight());
        drone = droneRepo.save(drone);




        BeanUtils.copyProperties(medicationDto, medication, "drone");
        medication.setDrone(drone);
        medication = medicationRepo.save(medication);

        BeanUtils.copyProperties(medication,medicationResponseDto, "id", "drone");
        medicationResponseDto.setMedicationId(medication.getUid());
        medicationResponseDto.setDroneId(medication.getDrone().getUid());
        baseResponse.setData(medicationResponseDto);
        baseResponse.setCode(HttpStatus.CREATED.value());


        return baseResponse;
    }

    @Override
    public MedicationDto getJson(String medication) {
        var medicationDto = new MedicationDto();
        try
        {
            var objectMapper = new ObjectMapper();
            medicationDto = objectMapper.readValue(medication,MedicationDto.class);

        } catch (JsonMappingException e) {
            throw new BadRequestException("An error occurred while processing input ");
        } catch (JsonProcessingException e) {
            throw new BadRequestException("An error occurred while processing input ");
        }
        return medicationDto;
    }
}
