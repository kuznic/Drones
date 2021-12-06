package musala.drones.repository;

import musala.drones.model.Drone;
import musala.drones.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>, JpaSpecificationExecutor<Drone> {
    Drone findByUid(UUID medicationUid);



}
