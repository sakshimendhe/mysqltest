package com.crio.mysqltest.services.impl;

import com.crio.mysqltest.dtos.SubjectDto;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.entities.Subject;
import com.crio.mysqltest.repositories.StudentRepository;
import com.crio.mysqltest.repositories.SubjectRepository;
import com.crio.mysqltest.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Override
    public Subject createSubject(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setSubjectId(subjectDto.getSubjectId());
        subject.setName(subjectDto.getName());

        if (subjectDto.getEnrolledStudents() != null) {
            subject.setEnrolledStudents(
                subjectDto.getEnrolledStudents().stream()
                    .map(registrationId -> studentRepository.findByRegistrationId(registrationId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Student Registration ID: " + registrationId)))
                    .collect(Collectors.toList())
            );
        }

        return subjectRepository.save(subject);
    }

    @Override
    public Optional<Subject> getSubject(String subjectId) {
        return subjectRepository.findBySubjectId(subjectId);
    }

    @Override
    public Subject updateSubject(String subjectId, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + subjectId));
        if (subjectDto.getName() != null) {
            subject.setName(subjectDto.getName());
        }
        if (subjectDto.getEnrolledStudents() != null) {
            subject.setEnrolledStudents(
                subjectDto.getEnrolledStudents().stream()
                    .map(registrationId -> studentRepository.findByRegistrationId(registrationId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Student Registration ID: " + registrationId)))
                    .collect(Collectors.toList())
            );
        }

        return subjectRepository.save(subject);
    }

    @Override
    public boolean deleteSubject(String subjectId) {
        Optional<Subject> subject = subjectRepository.findBySubjectId(subjectId);
        if (subject.isPresent()) {
            subjectRepository.delete(subject.get());
            return true;
        }
        return false;
    }
}
