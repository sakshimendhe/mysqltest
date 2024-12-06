package com.crio.mysqltest.services;

import com.crio.mysqltest.dtos.StudentDto;
import com.crio.mysqltest.entities.Student;

import java.util.Optional;

public interface StudentService {
    Student createStudent(StudentDto studentDto);
    Optional<Student> getStudent(String registrationId);
    Student updateStudent(String registrationId, StudentDto studentDto);
    boolean deleteStudent(String registrationId);
    Student enrollSubject(String registrationId, String subjectId);
    Student registerExamForStudent(String registrationId, String examId);
    StudentDto mapToDto(Student student);
}