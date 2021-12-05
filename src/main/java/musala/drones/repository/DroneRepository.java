package musala.drones.repository;

import musala.drones.model.Drone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface DroneRepository extends JpaRepository<Drone, Long>, JpaSpecificationExecutor<Drone> {
    Drone findByUid(UUID droneUid);
    Page<Drone> findAll(Pageable pageable);

    @Query(value="SELECT * FROM drones ",nativeQuery=true)
    List<Drone> getAllDrones(Pageable pageable);



}
