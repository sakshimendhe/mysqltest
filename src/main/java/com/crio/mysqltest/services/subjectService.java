package com.crio.mysqltest.services;

import com.crio.mysqltest.dtos.SubjectDto;
import com.crio.mysqltest.entities.Subject;

import java.util.Optional;

public interface SubjectService {
    Subject createSubject(SubjectDto subjectDto);
    Optional<Subject> getSubject(String subjectId);
    Subject updateSubject(String subjectId, SubjectDto subjectDto);
    boolean deleteSubject(String subjectId);
}
