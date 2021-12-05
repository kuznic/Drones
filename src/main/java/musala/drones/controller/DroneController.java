package musala.drones.controller;


import lombok.extern.slf4j.Slf4j;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.implementation.DroneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/drones")
public class DroneController {

    @Autowired
    DroneServiceImpl droneService;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponseDto> register(@Valid @RequestBody  DroneRegistrationDto drone)
    {

        return ResponseEntity.ok(droneService.registerDrone(drone));
    }

}
