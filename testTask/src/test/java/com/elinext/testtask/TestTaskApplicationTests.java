package com.elinext.testtask;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.University;
import com.elinext.testtask.repository.FacultyRepository;
import com.elinext.testtask.repository.StudentRepository;
import com.elinext.testtask.repository.UniversityRepository;
import com.elinext.testtask.service.FacultyService;
import com.elinext.testtask.service.StudentService;
import com.elinext.testtask.service.UniversityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestTaskApplicationTests {
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    private static University university;
    private static Faculty faculty;
    private static Student student;
    private static FacultyDto facultyDto;
    private static StudentDto studentDto;
    private static long usingTestUniversityId;

    @Test
    public void createUniversityTest() {
        destroy();
        createUniversity();
        Assertions.assertFalse(universityService.create("TestUniversity"));
        university = universityRepository.findByName("TestUniversity");
        Assertions.assertNotNull(university);
        destroy();
    }

    @Test
    public void createFacultyTest() {
        destroy();
        createUniversity();
        createFaculty();
        Assertions.assertEquals(faculty.getName(), facultyDto.getName());
        destroy();
    }

    @Test
    public void createStudentTest() {
        destroy();
        createUniversity();
        createFaculty();
        createStudent();
        Assertions.assertEquals(student.getFirstName(), studentDto.getFirstName());
        Assertions.assertEquals(student.getSecondName(), studentDto.getSecondName());
        Assertions.assertEquals(faculty.getName(), student.getFaculty().getName());
        destroy();
    }

    @Test
    public void deleteUniversityTest() {
        destroy();
        createUniversity();
        long universityId = universityRepository.findByName("TestUniversity").getId();
        Assertions.assertTrue(universityService.deleteUniversity(universityId));
        Assertions.assertSame(universityRepository.findByName("TestUniversity"), null);
    }

    @Test
    public void deleteFacultyTest() {
        destroy();
        createUniversity();
        createFaculty();
        long facultyId = facultyRepository.findByName("TestFaculty").getId();
        long universityId = universityRepository.findByName("TestUniversity").getId();
        Assertions.assertTrue(facultyService.deleteFaculty(facultyId));
        Assertions.assertTrue(universityService.deleteUniversity(universityId));
        Assertions.assertSame(facultyRepository.findByName("TestFaculty"), null);
        Assertions.assertSame(universityRepository.findByName("TestUniversity"), null);
    }
    @Test
    public void deleteStudentTest(){
        destroy();
        createUniversity();
        createFaculty();
        createStudent();
        long facultyId = facultyRepository.findByName("TestFaculty").getId();
        long universityId = universityRepository.findByName("TestUniversity").getId();
        long studentId = studentRepository.findByFirstNameAndSecondName("TestFirstName","TestSecondName").getId();
        Assertions.assertTrue(studentService.deleteStudent(studentId));
        Assertions.assertTrue(facultyService.deleteFaculty(facultyId));
        Assertions.assertTrue(universityService.deleteUniversity(universityId));
        Assertions.assertSame(studentRepository.findByFirstNameAndSecondName("TestFirstName","TestSecondName"), null);
        Assertions.assertSame(facultyRepository.findByName("TestFaculty"), null);
        Assertions.assertSame(universityRepository.findByName("TestUniversity"), null);
    }

    private void createUniversity() {
        universityService.create("TestUniversity");
    }

    private void createFaculty() {
        usingTestUniversityId = universityRepository.findByName("TestUniversity").getId();
        facultyDto = new FacultyDto();
        Assertions.assertFalse(facultyService.createFaculty(facultyDto));
        facultyDto.setUniversityId(usingTestUniversityId);
        facultyDto.setName("TestFaculty");
        Assertions.assertTrue(facultyService.createFaculty(facultyDto));
        long facultyId = facultyRepository.findByName("TestFaculty").getId();
        faculty = facultyRepository.findById(facultyId).orElseThrow(IllegalArgumentException::new);
    }

    private void createStudent() {
        long usingTestFacultyId = facultyRepository.findByName("TestFaculty").getId();
        studentDto = new StudentDto();
        studentDto.setFirstName("TestFirstName");
        studentDto.setSecondName("TestSecondName");
        studentDto.setFacultyId(usingTestFacultyId);
        Assertions.assertTrue(studentService.createStudent(studentDto));
        student = studentRepository.findByFirstNameAndSecondName("TestFirstName", "TestSecondName");
    }

    private void destroy() {
        student = studentRepository.findByFirstNameAndSecondName("TestFirstName", "TestSecondName");
        if (student != null) {
            studentRepository.delete(student);
        }
        faculty = facultyRepository.findByName("TestFaculty");
        if (faculty != null) {
            facultyRepository.delete(faculty);
        }
        university = universityRepository.findByName("TestUniversity");
        if (university != null) {
            universityRepository.delete(university);
        }
    }
}
