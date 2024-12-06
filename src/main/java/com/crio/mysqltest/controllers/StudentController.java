package com.crio.mysqltest.controllers;

import com.crio.mysqltest.dtos.StudentDto;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<Student> getStudent(@PathVariable String registrationId) {
        Optional<Student> student = studentService.getStudent(registrationId);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{registrationId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String registrationId, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(registrationId, studentDto));
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String registrationId) {
        boolean deleted = studentService.deleteStudent(registrationId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{registrationId}/enroll-subject")
    public ResponseEntity<StudentDto> enrollSubject(
        @PathVariable String registrationId,
        @RequestBody Map<String, String> request) {
    String subjectId = request.get("subjectId");
    Student updatedStudent = studentService.enrollSubject(registrationId, subjectId);
    return ResponseEntity.ok(studentService.mapToDto(updatedStudent));
}

@PostMapping("/exams/{examId}")
public ResponseEntity<Student> registerExamForStudent(
        @PathVariable String examId, 
        @RequestBody Map<String, String> request) {
    String registrationId = request.get("registrationId");
    Student updatedStudent = studentService.registerExamForStudent(registrationId, examId);
    return ResponseEntity.ok(updatedStudent);
}
}
