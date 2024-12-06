package com.crio.mysqltest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String registrationId;

    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "student_subject",  
        joinColumns = @JoinColumn(name = "student_id"), 
        inverseJoinColumns = @JoinColumn(name = "subject_id")  
    )
    private List<Subject> enrolledSubjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "student_exam", 
        joinColumns = @JoinColumn(name = "student_id"),  
        inverseJoinColumns = @JoinColumn(name = "exam_id")  
    )
    private List<Exam> registeredExams = new ArrayList<>();
}
