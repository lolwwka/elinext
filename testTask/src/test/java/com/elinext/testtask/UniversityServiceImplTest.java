package com.elinext.testtask;

import com.elinext.testtask.dto.FacultyDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.dto.UniversityDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.University;
import com.elinext.testtask.repository.UniversityRepository;
import com.elinext.testtask.service.UniversityServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UniversityServiceImplTest {
    private static final UniversityRepository UNIVERSITY_REPOSITORY = Mockito.spy(UniversityRepository.class);
    private static UniversityServiceImpl universityService;
    private static University university;
    private static Faculty faculty;
    private static Student student;

    @BeforeClass
    public static void setUp() {
        university = new University();
        faculty = new Faculty();
        student = new Student();
        university.setFaculties(new HashSet<>(Collections.singletonList(faculty)));
        faculty.setStudents(new ArrayList<>(Collections.singletonList(student)));
        universityService = new UniversityServiceImpl(UNIVERSITY_REPOSITORY);
        university.setName("BSUIR");
        university.setId(123L);
        faculty.setName("TestFaculty");
        student.setFirstName("TestFirstName");
        student.setSecondName("TestSecondName");
    }

    @Test
    public void updateTest() {
        Mockito.when(UNIVERSITY_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(university));
        UniversityDto universityToUpdate = new UniversityDto();
        universityToUpdate.setName("MED");
        universityService.updateUniversity(universityToUpdate, 213);
        Assertions.assertNotNull(university);
        Assertions.assertSame(university.getName(), universityToUpdate.getName());
    }

    @Test
    public void readTest() {
        Mockito.when(UNIVERSITY_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(university));
        UniversityDto universityDto = universityService.readUniversityDtoById(123);
        Assertions.assertNotNull(universityDto);
        FacultyDto testFaculty = universityDto.getFaculties().stream().findAny().orElse(null);
        Assertions.assertNotNull(testFaculty);
        StudentDto testStudent = testFaculty.getStudentDtoList().remove(0);
        Assertions.assertNotNull(testStudent);
        Assertions.assertSame(universityDto.getName(), university.getName());
        Assertions.assertSame(testFaculty.getName(), faculty.getName());
        Assertions.assertSame(testStudent.getFirstName(), student.getFirstName());
        Assertions.assertSame(testStudent.getSecondName(), student.getSecondName());
    }
}
