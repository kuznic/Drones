package musala.drones.service;

import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.MedicationDto;
import org.springframework.web.multipart.MultipartFile;


public interface MedicationService {

    BaseResponseDto addMedication(MedicationDto medicationDto, byte[] image);
    MedicationDto getJson(String medication);

}
