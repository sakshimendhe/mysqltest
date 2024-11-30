package com.crio.mysqltest.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key (auto-generated)

    @Column(unique = true, nullable = false)
    private String registrationId;  // Unique registration ID (non-primary key)

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_subject",  // Join table for Student-Subject many-to-many
        joinColumns = @JoinColumn(name = "student_id"),  // Foreign Key to Student
        inverseJoinColumns = @JoinColumn(name = "subject_id")  // Foreign Key to Subject
    )
    private List<Subject> enrolledSubjects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "student_exam",  // Join table for Student-Exam many-to-many
        joinColumns = @JoinColumn(name = "student_id"),  // Foreign Key to Student
        inverseJoinColumns = @JoinColumn(name = "exam_id")  // Foreign Key to Exam
    )
    private List<Exam> registeredExams = new ArrayList<>();
}
