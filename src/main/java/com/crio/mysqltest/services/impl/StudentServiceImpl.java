package com.crio.mysqltest.services.impl;

import com.crio.mysqltest.dtos.StudentDto;
import com.crio.mysqltest.dtos.SubjectDto;
import com.crio.mysqltest.entities.Exam;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.entities.Subject;
import com.crio.mysqltest.repositories.StudentRepository;
import com.crio.mysqltest.repositories.SubjectRepository;
import com.crio.mysqltest.repositories.ExamRepository;
import com.crio.mysqltest.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;

    @Override
    public StudentDto mapToDto(Student student) {
        List<SubjectDto> subjectDtos = student.getEnrolledSubjects()
            .stream()
            .map(subject -> new SubjectDto(
                subject.getSubjectId(),
                subject.getName(),
                subject.getEnrolledStudents()
                    .stream()
                    .map(Student::getRegistrationId) 
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());

        List<String> examIds = student.getRegisteredExams()
            .stream()
            .map(Exam::getExamId)
            .collect(Collectors.toList());

        return new StudentDto(
            student.getRegistrationId(),
            student.getName(),
            subjectDtos,  
            examIds
        );
    }

    @Override
    public Student createStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setRegistrationId(studentDto.getRegistrationId());
        student.setName(studentDto.getName());

        if (studentDto.getEnrolledSubjects() != null) {
            student.setEnrolledSubjects(
                studentDto.getEnrolledSubjects().stream()
                    .map(subjectDto -> subjectRepository.findBySubjectId(subjectDto.getSubjectId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Subject ID: " + subjectDto.getSubjectId())))
                    .collect(Collectors.toList())
            );
        }

        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getStudent(String registrationId) {
        return studentRepository.findByRegistrationId(registrationId);
    }

    @Override
    public Student updateStudent(String registrationId, StudentDto studentDto) {
        Student student = studentRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with Registration ID: " + registrationId));
        
        if (studentDto.getName() != null) {
            student.setName(studentDto.getName());
        }

        if (studentDto.getEnrolledSubjects() != null) {
            student.setEnrolledSubjects(
                studentDto.getEnrolledSubjects().stream()
                    .map(subjectDto -> subjectRepository.findBySubjectId(subjectDto.getSubjectId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Subject ID: " + subjectDto.getSubjectId())))
                    .collect(Collectors.toList())
            );
        }

        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(String registrationId) {
        Optional<Student> student = studentRepository.findByRegistrationId(registrationId);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return true;
        }
        return false;
    }

    @Override
    public Student enrollSubject(String registrationId, String subjectId) {
        Student student = studentRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Subject subject = subjectRepository.findBySubjectId(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));

        if (!student.getEnrolledSubjects().contains(subject)) {
            student.getEnrolledSubjects().add(subject);
            subject.getEnrolledStudents().add(student); 
            subjectRepository.save(subject);
        }

        return studentRepository.save(student);
    }

    @Override
    public Student registerExamForStudent(String registrationId, String examId) {
        Student student = studentRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        if (!student.getRegisteredExams().contains(exam)) {
            student.getRegisteredExams().add(exam);
        }

        return studentRepository.save(student);
    }
}
