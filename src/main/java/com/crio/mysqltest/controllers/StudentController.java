package com.crio.mysqltest.controllers;
import com.crio.mysqltest.entities.Exam;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.services.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {
     private final StudentService studentService;
    

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/students/{registrationId}")
    public ResponseEntity<Student> getStudent(@PathVariable String registrationId) {
        return ResponseEntity.of(studentService.getStudent(registrationId));
    }

    @PutMapping("/students/{registrationId}")
    public ResponseEntity<Student> updateStudent(@PathVariable String registrationId, @RequestBody Student student) {
        return ResponseEntity.of(studentService.updateStudent(registrationId, student));
    }

    @DeleteMapping("/students/{registrationId}")
    public ResponseEntity<String> deleteStudent(@PathVariable String registrationId) {
        boolean deleted = studentService.deleteStudent(registrationId);
        return deleted ? ResponseEntity.ok("Student deleted successfully") : ResponseEntity.notFound().build();
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

  

    @PostMapping("/{registrationId}/subjects/{subjectId}")
    public ResponseEntity<String> enrollSubject(
            @PathVariable String registrationId,
            @PathVariable String subjectId) {
        boolean success = studentService.enrollSubject(registrationId, subjectId);
        if (success) {
            return ResponseEntity.ok("Student enrolled in subject successfully");
        }
        return ResponseEntity.badRequest().body("Enrollment failed. Ensure the student and subject exist.");
    }

    @PostMapping("/{registrationId}/exams/{examId}")
    public ResponseEntity<String> registerForExam(
            @PathVariable String registrationId,
            @PathVariable String examId) {
        boolean success = studentService.registerForExam(registrationId, examId);
        if (success) {
            return ResponseEntity.ok("Student registered for exam successfully");
        }
        return ResponseEntity.badRequest().body("Exam registration failed. Ensure the student is enrolled in the subject.");
    }
}
