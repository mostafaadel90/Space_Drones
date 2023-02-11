package com.drones.mostafa.model;

import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(unique = true, updatable = false)
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    private Model model;
    @Max(value = 500, message = "Maximum Weight for a Drone is 500 gr")
    @Min(value = 1, message = "Minimum Weight for a Drone is 1 gr")
    private Integer weightLimitInGrams;
    private Integer remainingWeightInGrams;
    @Range(min = 10, max = 100, message = "Battery Range should be from 10% to 100% for a new Drone")
    private Integer batteryCapacityPercentage;
    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drone", cascade = CascadeType.ALL)
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
