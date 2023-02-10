package com.drones.mostafa.model;

import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "serialNumber", unique = true ,updatable = false)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    private Integer weightLimitInGrams;
    private Integer remainingWeight;
    private Integer batteryCapacityPercentage;
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "drone",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Medication> medications = new ArrayList<>();
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
