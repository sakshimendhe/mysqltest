package com.crio.mysqltest.services;
import lombok.RequiredArgsConstructor;
import com.crio.mysqltest.entities.Student;
import com.crio.mysqltest.entities.Subject;
import com.crio.mysqltest.entities.Exam;
import com.crio.mysqltest.repositories.StudentRepository;
import com.crio.mysqltest.repositories.SubjectRepository;
import com.crio.mysqltest.repositories.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudent(String registrationId) {
        return studentRepository.findByRegistrationId(registrationId);
    }

    public Optional<Student> updateStudent(String registrationId, Student studentDetails) {
        Optional<Student> studentOpt = studentRepository.findByRegistrationId(registrationId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setName(studentDetails.getName());
            return Optional.of(studentRepository.save(student));
        }
        return Optional.empty();
    }

    public boolean deleteStudent(String registrationId) {
        Optional<Student> studentOpt = studentRepository.findByRegistrationId(registrationId);
        if (studentOpt.isPresent()) {
            studentRepository.delete(studentOpt.get());
            return true;
        }
        return false;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public boolean enrollSubject(String registrationId, String subjectId) {
        Optional<Student> studentOpt = studentRepository.findByRegistrationId(registrationId);
        Optional<Subject> subjectOpt = subjectRepository.findBySubjectId(subjectId);
    
        if (studentOpt.isPresent() && subjectOpt.isPresent()) {
            Student student = studentOpt.get();
            Subject subject = subjectOpt.get();

            if (!student.getEnrolledSubjects().contains(subject)) {
                student.getEnrolledSubjects().add(subject);
                subject.getEnrolledStudents().add(student);
                studentRepository.save(student);  
                subjectRepository.save(subject); 
                return true;  
            }
        }
        return false;
    }
    
    public boolean registerForExam(String registrationId, String examId) {
        Optional<Student> studentOpt = studentRepository.findByRegistrationId(registrationId);
        Optional<Exam> examOpt = examRepository.findByExamId(examId);
        if (studentOpt.isPresent() && examOpt.isPresent()) {
            Student student = studentOpt.get();
            Exam exam = examOpt.get();
            if (student.getEnrolledSubjects().contains(exam.getSubject())) {
                if (!student.getRegisteredExams().contains(exam)) {
                    student.getRegisteredExams().add(exam);
                    exam.getEnrolledStudents().add(student);
                    studentRepository.save(student);
                    examRepository.save(exam);
                    return true;
                } else {
                    return false; 
                }
            } else {
                return false;
            }
        }
        return false;
    }
    
}
