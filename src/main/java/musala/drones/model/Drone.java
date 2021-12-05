package musala.drones.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Size(max = 100,message="{drone.allowed.serialNumberSize}")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model", nullable = false,updatable = false)
    private DroneModel droneModel;

    @Column(name = "weight_lim_in_gr",nullable = false,updatable = false, columnDefinition = "smallint")
    private int weightLimit;

    @Column(name = "battery_capacity", nullable = false, columnDefinition = "tinyint")
    @DecimalMax(value="100", message = "{drone.max.batteryCapacity}")
    @DecimalMin(value="0", message = "{drone.min.batteryCapacity}")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state", nullable = false)
    private DroneState droneState;


    @Column(name= "loaded_weight",columnDefinition = "integer default 0", nullable = false)
    private int weight;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
    private List<Medication> medications ;




}
