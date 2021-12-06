package musala.drones.controller;



import lombok.extern.slf4j.Slf4j;
import lombok.var;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.dto.MedicationDto;
import musala.drones.exception.NotFoundException;
import musala.drones.implementation.DroneServiceImpl;
import musala.drones.implementation.MedicationServiceImpl;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/drones")
public class dispatchController {

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
        var medicationDto = medicationService.getJson(medication);
        //return ResponseEntity.ok().body("success");

        return ResponseEntity.ok().body(medicationService.addMedication(medicationDto, bytes));
    }


}
