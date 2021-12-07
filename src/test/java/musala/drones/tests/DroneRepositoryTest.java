package musala.drones.tests;

import musala.drones.model.Drone;
import musala.drones.repository.DroneRepository;
import musala.drones.utility.enums.DroneState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@DataJpaTest
public class DroneRepositoryTest
{

    @Autowired
    private DroneRepository droneRepo;

    private final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));


    @Test
    void findByUid_success()
    {
        UUID droneId = UUID.fromString("06364cbc-9468-4bfe-a917-1ab610bd49f1");
        Drone drone = droneRepo.findByUid(droneId);
        assert(drone != null);
    }

    @Test
    void findAll_success()
    {

        Page<Drone> allDrones = droneRepo.findAll(pageable);
        assert(allDrones.getTotalElements()) > 1;
    }

    @Test
    void getAllDronesAvailableForLoading_success()
    {
        List<Drone> allAvailableDrones = droneRepo.getAllDronesAvailableForLoading(pageable);
        for(Drone drone: allAvailableDrones)
        {
            assert (drone.getDroneState().equals(DroneState.LOADING));
        }
    }


}
