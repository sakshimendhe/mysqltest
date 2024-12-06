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
    private Long id; 

    @Column(unique = true, nullable = false) 
    private String examId;

    @ManyToOne
    @JoinColumn(name = "subject_id")  
    private Subject subject;

    @ManyToMany(mappedBy = "registeredExams")
    private List<Student> enrolledStudents = new ArrayList<>();
}