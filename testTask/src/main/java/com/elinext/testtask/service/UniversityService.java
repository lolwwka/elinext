package com.elinext.testtask.service;

import com.elinext.testtask.dto.UniversityDto;
import com.elinext.testtask.entity.University;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UniversityService {
    boolean create(String name);
    boolean deleteUniversity(long id);
    University readById(long id);
    UniversityDto updateUniversity(UniversityDto universityDto, long id);
    UniversityDto readUniversityDtoById(long id);
}
