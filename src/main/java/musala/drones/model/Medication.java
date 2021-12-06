package musala.drones.model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "medications",  uniqueConstraints =  {@UniqueConstraint(name = "UniqueName", columnNames = { "name_of_medication",})})
public class Medication extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "medication_uid", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uid = UUID.randomUUID();

    @Column(name = "name_of_medication", nullable = false)
    private String name;

    @Column(name = "weight_of_medication_in_gr",nullable = false)
    private int weight;

    @Column(name = "medication_code", nullable = false )
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "img_id_of_medication", referencedColumnName = "id")
    private MedicationImage image;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Medication that = (Medication) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
