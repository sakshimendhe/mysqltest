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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Primary key (auto-generated)

    @Column(unique = true, nullable = false)
    private String subjectId;  // Unique subject ID (non-primary key)

    private String name;

    @ManyToMany(mappedBy = "enrolledSubjects")  // "enrolledSubjects" is the name of the field in Student class
    private List<Student> enrolledStudents = new ArrayList<>();
}
