package musala.drones.repository;

import musala.drones.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface DroneRepo extends JpaRepository<Drone, UUID>, JpaSpecificationExecutor<Drone> {
    Drone findByUid(UUID droneUid);



}