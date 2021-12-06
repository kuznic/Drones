package musala.drones.model;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "drones", uniqueConstraints =  {@UniqueConstraint(name = "UniqueSerialNumber", columnNames = { "serial_number",})})
public class Drone extends AbstractAuditingEntity implements Serializable {
    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "drone_uid", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uid = UUID.randomUUID();

    @Column(name = "serial_number", nullable = false, updatable = false, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model", nullable = false,updatable = false)
    private DroneModel droneModel;

    @Column(name = "weight_lim_in_gr",nullable = false,updatable = false, columnDefinition = "smallint")
    private int weightLimit;

    @Column(name = "battery_capacity", nullable = false, columnDefinition = "tinyint")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state", nullable = false)
    private DroneState droneState;


    @Column(name= "loaded_weight",columnDefinition = "integer default 0", nullable = false)
    private int weight;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Medication> medications ;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Drone drone = (Drone) o;
        return id != null && Objects.equals(id, drone.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
