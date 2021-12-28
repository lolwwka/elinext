package com.elinext.testtask.service;

import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Subject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SubjectService {
    boolean createSubject(SubjectDto subjectDto);
    boolean updateSubject(long subjectId, SubjectDto subjectDto);
    boolean deleteSubjectById(long id);
    SubjectDto getSubjectInfo(long id);
    Subject getSubjectById(long id);
}
