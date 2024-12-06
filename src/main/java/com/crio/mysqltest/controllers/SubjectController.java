package com.crio.mysqltest.controllers;

import com.crio.mysqltest.dtos.SubjectDto;
import com.crio.mysqltest.entities.Subject;
import com.crio.mysqltest.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.createSubject(subjectDto));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getSubject(@PathVariable String subjectId) {
        Optional<Subject> subject = subjectService.getSubject(subjectId);
        return subject.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> updateSubject(@PathVariable String subjectId, @RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.updateSubject(subjectId, subjectDto));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String subjectId) {
        boolean deleted = subjectService.deleteSubject(subjectId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
