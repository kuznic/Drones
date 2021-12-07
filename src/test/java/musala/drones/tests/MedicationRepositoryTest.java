package musala.drones.tests;

import musala.drones.model.Medication;
import musala.drones.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MedicationRepositoryTest
{

    @Autowired
    private MedicationRepository medicationRepo;

    private final Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));


    @Test
    void getAllMedicationsForDrone_success()
    {
        Long id = 20L;
        List<Medication> allAvailableDrones = medicationRepo.getAllMedicationsForDrone(id,pageable);
        assert(allAvailableDrones.size() == 1);
    }


}
