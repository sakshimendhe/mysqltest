package com.crio.mysqltest.services;

import com.crio.mysqltest.dtos.ExamDto;
import com.crio.mysqltest.entities.Exam;

import java.util.Optional;

public interface ExamService {
    Exam createExam(ExamDto examDto);
    Optional<Exam> getExam(String examId);
    Exam updateExam(String examId, ExamDto examDto);
    boolean deleteExam(String examId);
}
