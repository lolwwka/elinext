package com.elinext.testtask.service;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.entity.Faculty;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FacultyService {
    boolean createFaculty(FacultyDto facultyDto);
    boolean deleteFaculty(long id);
    Faculty readById(long id);
    boolean updateFaculty(FacultyDto facultyDto, long id);
    FacultyDto readFacultyDtoById(long id);
}
