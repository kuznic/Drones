package musala.drones.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import musala.drones.dto.*;
import musala.drones.exception.BadRequestException;
import musala.drones.model.Medication;
import musala.drones.repository.DroneRepository;
import musala.drones.repository.MedicationRepository;
import musala.drones.service.MedicationService;
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
    public BaseResponseDto addMedication(MedicationDto medicationDto, byte[] image) {
        var medication = new Medication();
        var baseResponse = new BaseResponseDto();
        var medicationResponseDto = new MedicationResponseDto();
        var drone = droneRepo.findByUid(medicationDto.getDroneId());


        if(!medicationDto.getName().matches("^[a-zA-Z0-9_-]*$"))
        {
            throw  new BadRequestException("Only letters, numbers, underscore and dash are allowed for name");
        }

        if(!medicationDto.getCode().matches("^[A-Z0-9_]*$"))
        {
            throw new BadRequestException("Only upper case letters, underscore and numbers are allowed for code");
        }

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
        medication.setImage(image);
        medication = medicationRepo.save(medication);

        BeanUtils.copyProperties(medication,medicationResponseDto, "id", "drone");
        medicationResponseDto.setMedicationId(medication.getUid());
        medicationResponseDto.setDroneId(medication.getDrone().getUid());
        medicationResponseDto.setImageId(medication.getImg_uid());
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

        } catch (JsonProcessingException e) {
            throw new BadRequestException("An error occurred while processing medication");
        }
        return medicationDto;
    }

    @Override
    public BaseResponseDto getAllDroneMedications(UUID droneId, Pageable pageable) {
        var drone = droneRepo.findByUid(droneId);
        return Mono.just(pageable)
                .subscribeOn(Schedulers.parallel())
                .map(page->{
                    var baseResponseDto = new BaseResponseDto();
                    baseResponseDto.setCode(HttpStatus.OK.value());
                    baseResponseDto.setMessage(HttpStatus.OK.getReasonPhrase());
                    baseResponseDto.setData(generateMedicationResponseDto(medicationRepo.getAllMedicationsForDrone(drone.getId(),pageable)));
                    return baseResponseDto;
                }).block();
    }

    private List<MedicationResponseDto> generateMedicationResponseDto(List<Medication> medicationList){

        return medicationList.stream()
                .map(medication -> {
                    var medicationResponseDto = new MedicationResponseDto();
                    BeanUtils.copyProperties(medication,medicationResponseDto,"id");
                    medicationResponseDto.setMedicationId(medication.getUid());
                    medicationResponseDto.setWeight(medication.getWeight());
                    medicationResponseDto.setDroneId(medication.getDrone().getUid());
                    medicationResponseDto.setImageId(medication.getImg_uid());
                    return medicationResponseDto;
                }).collect(Collectors.toList());
    }
}
