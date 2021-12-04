package musala.drones.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Slf4j
@Data
@Table(name = "drones", uniqueConstraints =  {@UniqueConstraint(name = "UniqueSerialNumber", columnNames = { "serial_number",})})
public class Drone implements Serializable {
    private static final long serialVersionId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "drone_uid", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uid;

    @Column(name = "serial_number", nullable = false, updatable = false, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model", nullable = false,updatable = false)
    private DroneModel droneModel;

    @Column(name = "weight_lim_in_gr",nullable = false,updatable = false)
    private int weightLimit;

    @Column(name = "battery_capacity",precision = 5, scale = 2, nullable = false, columnDefinition = "decimal(5,2)")
    private float batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state", nullable = false)
    private DroneState droneState;




}
