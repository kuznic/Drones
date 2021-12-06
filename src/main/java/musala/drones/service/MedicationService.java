package musala.drones.service;

import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.MedicationDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface MedicationService {

    BaseResponseDto addMedication(MedicationDto medicationDto, byte[] image);
    MedicationDto getJson(String medication);
    BaseResponseDto getAllDroneMedications(UUID droneID, Pageable pageable);

}
