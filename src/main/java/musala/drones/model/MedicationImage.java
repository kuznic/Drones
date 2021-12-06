package musala.drones.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="medication_images")
@Data
public class MedicationImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


    @Column(name = "med_img_uid", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uid = UUID.randomUUID();

    @Lob
    @Column(name ="img_of_medication", nullable = false)
    private byte[] image;

    @OneToOne(mappedBy = "image")
    private Medication medication;
}
