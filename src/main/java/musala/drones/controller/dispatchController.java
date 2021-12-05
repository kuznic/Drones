package musala.drones.controller;



import lombok.extern.slf4j.Slf4j;
import musala.drones.dto.BaseResponseDto;
import musala.drones.dto.DroneRegistrationDto;
import musala.drones.implementation.DroneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/drones")
public class dispatchController {

    @Autowired
    DroneServiceImpl droneService;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponseDto> registerDrone(@Valid @RequestBody  DroneRegistrationDto drone)
    {

        return ResponseEntity.ok(droneService.registerDrone(drone));
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<BaseResponseDto>getAllDrones(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "100") int size) {
        {
            //User user = userRepo.findById(JwtTokenProvider.loggedId.intValue()).get();
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            return ResponseEntity.ok().body(droneService.getDroneList(pageable));
        }
    }

}
