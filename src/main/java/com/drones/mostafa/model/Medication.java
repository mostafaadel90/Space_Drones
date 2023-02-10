package com.drones.mostafa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Pattern(regexp = "^[A-Za-z0-9_\\-]+", message = "Medication name allow only letters, numbers, '-', '_'")
    private String name;
    private Integer weightInGrams;
    @Pattern(regexp = "^[A-Z0-9_]+", message = "Medication Code allow only upper case letters, numbers and '_'")
    private String code;

    private String image;
    @ManyToOne
    @JoinColumn(name = "drone_id")
    @JsonIgnore
    private Drone drone;

}
