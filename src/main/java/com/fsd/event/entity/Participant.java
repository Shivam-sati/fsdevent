package com.fsd.event.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participant")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "participantId")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;

    private String name;
    private String email;
    private String phone;

    @Column(name = "college_name")
    private String collegeName;

    // When serializing activities, ignore the lazy proxy properties of Activity
    @ManyToMany(mappedBy = "participants")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "event"})
    private List<Activity> activities = new ArrayList<>();
}
