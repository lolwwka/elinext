package com.elinext.testtask.service;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.dto.UniversityDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.University;
import com.elinext.testtask.repository.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService{
    private final UniversityRepository universityRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public boolean create(String name) {
        if(universityRepository.findByName(name)!= null){
            return false;
        }
        University university = new University();
        university.setName(name);
        universityRepository.save(university);
        return true;
    }

    @Override
    public boolean deleteUniversity(long id) {
        universityRepository.deleteById(id);
        return universityRepository.findById(id).isEmpty();
    }

    @Override
    public University readById(long id) {
        return universityRepository.findById(id).orElse(null);
    }

    @Override
    public UniversityDto updateUniversity(UniversityDto universityDto, long id) {
        University university = universityRepository.findById(id).orElse(null);
        if(university == null){
            return new UniversityDto(true);
        }
        university.setName(universityDto.getName());
        return universityDto;
    }

    @Override
    public UniversityDto readUniversityDtoById(long id) {
        University university = readById(id);
        if(university == null){
            UniversityDto universityDto = new UniversityDto();
            universityDto.setError(true);
            return universityDto;
        }
        return convertUniversityToDto(university);
    }
    private UniversityDto convertUniversityToDto(University university){
        UniversityDto universityDto = new UniversityDto();
        universityDto.setName(university.getName());
        HashSet<FacultyDto> faculties = new HashSet<>();
        for(Faculty faculty : university.getFaculties()){
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setName(faculty.getName());
            facultyDto.setUniversityId(university.getId());
            facultyDto.setFacultyId(faculty.getId());
            List<StudentDto> studentDtoList = new ArrayList<>();
            for(Student student : faculty.getStudents()){
                StudentDto studentDto = new StudentDto();
                studentDto.setFirstName(student.getFirstName());
                studentDto.setSecondName(student.getSecondName());
                studentDtoList.add(studentDto);
            }
            facultyDto.setStudentDtoList(studentDtoList);
            faculties.add(facultyDto);
        }
        universityDto.setFaculties(faculties);
        return universityDto;
    }
}
