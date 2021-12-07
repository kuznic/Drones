package musala.drones.controller;



import lombok.extern.slf4j.Slf4j;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.dto.MedicationDto;
import musala.drones.exception.NotFoundException;
import musala.drones.implementation.DroneServiceImpl;
import musala.drones.implementation.MedicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/drones")
public class DispatchController {

    @Autowired
    DroneServiceImpl droneService;

    @Autowired
    MedicationServiceImpl medicationService;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponseDto> registerDrone(@Valid @RequestBody DroneRegistrationDto drone) {

        return ResponseEntity.ok(droneService.registerDrone(drone));
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> getAllDrones(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return ResponseEntity.ok().body(droneService.getDroneList(pageable));
    }

    @GetMapping("/{droneUid}")
    public ResponseEntity<?> getDrone(@PathVariable("droneUid") UUID droneUid) {
        return ResponseEntity.ok(droneService.getDrone(droneUid));
    }

    @PatchMapping("/{droneUid}")
    public ResponseEntity<?> prepareDroneForLoading(@PathVariable("droneUid") UUID droneUid) {
        return ResponseEntity.ok(droneService.prepareDroneForLoading(droneUid));
    }

    @GetMapping("/available-for-loading")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> getAllAvailableDrones(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return ResponseEntity.ok().body(droneService.getAvailableDronesForLoading(pageable));
    }


    @PostMapping(path = "/add-medication", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addMedication(@RequestPart("file") MultipartFile file, @RequestPart("medication") String medication) throws IOException
    {
        if (file == null) throw new NotFoundException("document not found");
        byte[] bytes = file.getBytes();

        MedicationDto medicationDto = medicationService.getJson(medication);

        return ResponseEntity.ok().body(medicationService.addMedication(medicationDto, bytes));
    }

    @GetMapping("/drone-medications/{droneUid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponseDto> getAllDroneMedications(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @PathVariable("droneUid") UUID droneUid) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return ResponseEntity.ok().body(medicationService.getAllDroneMedications(droneUid,pageable));
    }

    @GetMapping("/battery-level/{droneUid}")
    public ResponseEntity<?> getDroneBatteryLevel(@PathVariable("droneUid") UUID droneUid) {
        return ResponseEntity.ok(droneService.getDroneBatteryLevel(droneUid));
    }


}
