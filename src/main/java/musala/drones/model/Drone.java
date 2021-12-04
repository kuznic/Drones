package musala.drones.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import musala.drones.utility.enums.DroneModel;
import musala.drones.utility.enums.DroneState;

import javax.persistence.*;

@Entity
@Slf4j
@Data
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "serial_number", nullable = false, updatable = false, length = 100,unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_model", nullable = false,updatable = false)
    private DroneModel droneModel;

    @Column(name = "weight_lim_in_gr",nullable = false,updatable = false)
    private int weightLimit;

    @Column(name = "battery_capacity",precision = 5, scale = 2, nullable = false)
    private float batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "drone_state", nullable = false)
    private DroneState droneState;




}
