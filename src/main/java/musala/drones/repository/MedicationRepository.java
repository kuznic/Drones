package musala.drones.repository;

import musala.drones.model.Drone;
import musala.drones.model.Medication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>, JpaSpecificationExecutor<Drone> {
    Drone findByUid(UUID medicationUid);

    @Query(value="SELECT * FROM medications  WHERE drone_id=:droneId",nativeQuery=true)
    List<Medication> getAllMedicationsForDrone(@Param ("droneId")Long droneId, Pageable pageable);




}
