package com.crio.mysqltest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private String examId;
    private String subjectId;            
    private List<String> enrolledStudents; 
}

