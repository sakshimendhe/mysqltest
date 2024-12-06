package com.crio.mysqltest.controllers;

import com.crio.mysqltest.dtos.ExamDto;
import com.crio.mysqltest.entities.Exam;
import com.crio.mysqltest.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody ExamDto examDto) {
        return ResponseEntity.ok(examService.createExam(examDto));
    }

    @GetMapping("/{examId}")
    public ResponseEntity<Exam> getExam(@PathVariable String examId) {
        Optional<Exam> exam = examService.getExam(examId);
        return exam.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{examId}")
    public ResponseEntity<Exam> updateExam(@PathVariable String examId, @RequestBody ExamDto examDto) {
        return ResponseEntity.ok(examService.updateExam(examId, examDto));
    }

    @DeleteMapping("/{examId}")
    public ResponseEntity<Void> deleteExam(@PathVariable String examId) {
        boolean deleted = examService.deleteExam(examId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
