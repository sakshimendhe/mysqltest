package com.crio.mysqltest.services.impl;

import com.crio.mysqltest.dtos.ExamDto;
import com.crio.mysqltest.entities.Exam;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.entities.Subject;
import com.crio.mysqltest.repositories.ExamRepository;
import com.crio.mysqltest.repositories.StudentRepository;
import com.crio.mysqltest.repositories.SubjectRepository;
import com.crio.mysqltest.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Override
    public Exam createExam(ExamDto examDto) {
      
        Exam exam = new Exam();
        exam.setExamId(examDto.getExamId());

    
        Subject subject = subjectRepository.findBySubjectId(examDto.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + examDto.getSubjectId()));
        exam.setSubject(subject);

       
        if (examDto.getEnrolledStudents() != null) {
            exam.setEnrolledStudents(
                examDto.getEnrolledStudents().stream()
                    .map(registrationId -> {
                        Student student = studentRepository.findByRegistrationId(registrationId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Student Registration ID: " + registrationId));
                        if (!student.getEnrolledSubjects().contains(subject)) {
                            throw new IllegalArgumentException("Student with ID: " + registrationId + " is not enrolled in the subject: " + subject.getSubjectId());
                        }
                        return student;
                    })
                    .collect(Collectors.toList())
            );
        }

        return examRepository.save(exam);
    }

    @Override
    public Optional<Exam> getExam(String examId) {
        return examRepository.findByExamId(examId);
    }

    @Override
    public Exam updateExam(String examId, ExamDto examDto) {
     
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with ID: " + examId));

        if (examDto.getSubjectId() != null) {
            Subject subject = subjectRepository.findBySubjectId(examDto.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + examDto.getSubjectId()));
            exam.setSubject(subject);
        }

        if (examDto.getEnrolledStudents() != null) {
            exam.setEnrolledStudents(
                examDto.getEnrolledStudents().stream()
                    .map(registrationId -> {
                        Student student = studentRepository.findByRegistrationId(registrationId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Student Registration ID: " + registrationId));
                        if (!student.getEnrolledSubjects().contains(exam.getSubject())) {
                            throw new IllegalArgumentException("Student with ID: " + registrationId + " is not enrolled in the subject: " + exam.getSubject().getSubjectId());
                        }
                        return student;
                    })
                    .collect(Collectors.toList())
            );
        }

        return examRepository.save(exam);
    }

    @Override
    public boolean deleteExam(String examId) {
        Optional<Exam> exam = examRepository.findByExamId(examId);
        if (exam.isPresent()) {
            examRepository.delete(exam.get());
            return true;
        }
        return false;
    }
}
