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
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key (auto-generated)

    @Column(unique = true, nullable = false)  // Unique identifier for the exam
    private String examId;

    @ManyToOne
    @JoinColumn(name = "subject_id")  // Foreign Key to Subject
    private Subject subject;

    @ManyToMany(mappedBy = "registeredExams")  // "registeredExams" is the name of the field in Student class
    private List<Student> enrolledStudents = new ArrayList<>();
}