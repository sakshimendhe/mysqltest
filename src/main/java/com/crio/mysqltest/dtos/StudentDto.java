package com.crio.mysqltest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String registrationId;
    private String name;
    private List<SubjectDto> enrolledSubjects; 
    private List<String> registeredExams; 
}
