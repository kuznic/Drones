package musala.drones.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "medication",  uniqueConstraints =  {@UniqueConstraint(name = "UniqueName", columnNames = { "name_of_medication",})})
public class Medication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "medication_uid", updatable = false, nullable = false, length = 32, columnDefinition = "uuid")
    private UUID uid;

    @Column(name = "name_of_medication", nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$",message = "Only letters, numbers, underscore and dash are allowed")
    private String name;

    @Column(name = "weight_of_medication_in_gr",nullable = false)
    private int weight;

    @Column(name = "medication_code", nullable = false )
    @Pattern(regexp = "^[A-Z0-9_]*$",message = "Only upper case letters, underscore and numbers are allowed")
    private String code;

    @Lob
    @Column(name ="img_of_medication", nullable = false)
    private byte[] image;

    @ManyToOne(optional = false)
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;
}
