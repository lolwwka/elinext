package com.elinext.testtask;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.University;
import com.elinext.testtask.repository.FacultyRepository;
import com.elinext.testtask.repository.StudentRepository;
import com.elinext.testtask.service.FacultyService;
import com.elinext.testtask.service.FacultyServiceImpl;
import com.elinext.testtask.service.UniversityService;
import com.elinext.testtask.service.UniversityServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FacultyServiceImplTest {
    private static final FacultyRepository FACULTY_REPOSITORY = Mockito.mock(FacultyRepository.class);
    private static final UniversityService UNIVERSITY_SERVICE = Mockito.mock(UniversityServiceImpl.class);
    private static final StudentRepository STUDENT_REPOSITORY = Mockito.mock(StudentRepository.class);
    private static FacultyService facultyService;
    private static Faculty faculty;
    private static Student student;
    private static University university;

    @BeforeClass
    public static void setUp() {
        faculty = new Faculty();
        student = new Student();
        university = new University();
        faculty.setName("TestFaculty");
        faculty.setUniversity(university);
        faculty.setStudents(new ArrayList<>(Collections.singletonList(student)));
        university.setName("TestUniversity");
        student.setFirstName("TestFirstName");
        student.setSecondName("TestSecondName");
        facultyService = new FacultyServiceImpl(FACULTY_REPOSITORY, UNIVERSITY_SERVICE, STUDENT_REPOSITORY);
        Mockito.when(FACULTY_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(faculty));
        Mockito.when(STUDENT_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(student));
    }

    @Test
    public void updateTest() {
        Student updatedStudent = new Student();
        Mockito.when(STUDENT_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(updatedStudent));
        updatedStudent.setFirstName("Andrew");
        updatedStudent.setSecondName("Waskovsky");
        University updatedUniversity = new University();
        updatedUniversity.setName("UpdatedUniversity");
        Mockito.when(UNIVERSITY_SERVICE.readById(Mockito.any(Long.class))).thenReturn(updatedUniversity);
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setName("MathFac");
        StudentDto studentDto = new StudentDto(1);
        facultyDto.setStudentDtoList(new ArrayList<>(Collections.singletonList(studentDto)));
        facultyDto.setUniversityId(123);
        facultyService.updateFaculty(facultyDto, 123);
        Assertions.assertEquals(faculty.getName(), facultyDto.getName());
        Assertions.assertEquals(updatedUniversity.getName(), faculty.getUniversity().getName());
        Assertions.assertEquals(updatedStudent.getFirstName(), faculty.getStudents().get(1).getFirstName());
        Assertions.assertEquals(updatedStudent.getSecondName(), faculty.getStudents().get(1).getSecondName());
    }

    @Test
    public void readTest() {
        FacultyDto facultyDto = facultyService.readFacultyDtoById(123);
        Assertions.assertNotNull(facultyDto);
        Assertions.assertEquals(facultyDto.getName(), faculty.getName());
        Assertions.assertEquals(facultyDto.getStudentDtoList().get(0).getFirstName(), student.getFirstName());
        Assertions.assertEquals(facultyDto.getStudentDtoList().get(0).getSecondName(), student.getSecondName());
        Assertions.assertSame(facultyDto.getFacultyId(), university.getId());
    }
}
