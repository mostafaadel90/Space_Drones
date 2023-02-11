package com.drones.mostafa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Audited()
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @NotAudited
    private Integer id;
    @Pattern(regexp = "^[A-Za-z0-9_\\-]+", message = "Medication name allow only letters, numbers, '-', '_'")
    @NotAudited
    private String name;
    @NotAudited
    private Integer weightInGrams;
    @NotAudited
    @Pattern(regexp = "^[A-Z0-9_]+", message = "Medication Code allow only upper case letters, numbers and '_'")
    private String code;
    @NotAudited
    private String image;
    @ManyToOne
    @JoinColumn(name = "drone_id")
    @JsonIgnore
    @NotAudited
    private Drone drone;

}
