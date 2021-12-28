package com.elinext.testtask.service;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.University;
import com.elinext.testtask.repository.FacultyRepository;
import com.elinext.testtask.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService{
    private final FacultyRepository facultyRepository;
    private final UniversityService universityService;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, UniversityService universityService, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.universityService = universityService;
        this.studentRepository = studentRepository;
    }

    @Override
    public boolean createFaculty(FacultyDto facultyDto) {
        if(facultyRepository.findByName(facultyDto.getName())!= null ||
                universityService.readById(facultyDto.getUniversityId()) == null){
            return false;
        }
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        University university = universityService.readById(facultyDto.getUniversityId());
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return true;
    }

    @Override
    public boolean deleteFaculty(long id) {
        facultyRepository.deleteById(id);
        return facultyRepository.findById(id).isEmpty();
    }

    @Override
    public Faculty readById(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateFaculty(FacultyDto facultyDto, long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Incorrect facultyId"));
        if (facultyDto.getName() != null) faculty.setName(facultyDto.getName());
        if (facultyDto.getUniversityId() != 0) {
            faculty.setUniversity(universityService.readById(facultyDto.getUniversityId()));
        }
        for (StudentDto studentDto : facultyDto.getStudentDtoList()) {
            Student student = studentRepository.findById(studentDto.getStudentId()).orElseThrow(() ->
                    new IllegalArgumentException("Student incorrect"));
            faculty.getStudents().add(student);
        }
        return true;
    }

    @Override
    public FacultyDto readFacultyDtoById(long id) {
        Faculty faculty = readById(id);
        if(faculty == null){
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setError(true);
            return facultyDto;
        }
        return convertFacultyToDto(faculty);
    }

    private FacultyDto convertFacultyToDto(Faculty faculty){
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setFacultyId(faculty.getId());
        facultyDto.setName(faculty.getName());
        facultyDto.setUniversityId(faculty.getUniversity().getId());
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student student : faculty.getStudents()){
            StudentDto studentDto = new StudentDto();
            studentDto.setFirstName(student.getFirstName());
            studentDto.setSecondName(student.getSecondName());
            studentDto.setStudentId(student.getId());
            studentDtoList.add(studentDto);
        }
        facultyDto.setStudentDtoList(studentDtoList);
        return facultyDto;
    }
}
