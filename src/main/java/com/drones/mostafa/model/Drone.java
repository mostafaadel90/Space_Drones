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
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Audited
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @NotAudited
    private Integer id;

    @Column(unique = true, updatable = false)
    @NotAudited
    private String serialNumber;
    @Enumerated(EnumType.STRING)
    @NotAudited
    private Model model;
    @Max(value = 500, message = "Maximum Weight for a Drone is 500 gr")
    @Min(value = 1, message = "Minimum Weight for a Drone is 1 gr")
    @NotAudited
    private Integer weightLimitInGrams;
    @NotAudited
    private Integer remainingWeightInGrams;
    @Range(min = 10, max = 100, message = "Battery Range should be from 10% to 100% for a new Drone")
    private Integer batteryCapacityPercentage;
    @NotAudited
    @Enumerated(EnumType.STRING)
    private State state = State.IDLE;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drone", cascade = CascadeType.ALL)
    @ToString.Exclude
    @NotAudited
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
