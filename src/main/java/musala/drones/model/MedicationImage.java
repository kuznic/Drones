package musala.drones.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="medication_images")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MedicationImage extends AbstractAuditingEntity implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MedicationImage that = (MedicationImage) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
