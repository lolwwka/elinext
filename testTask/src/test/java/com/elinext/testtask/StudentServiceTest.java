package com.elinext.testtask;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Schedule;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.Subject;
import com.elinext.testtask.repository.FacultyRepository;
import com.elinext.testtask.repository.ScheduleRepository;
import com.elinext.testtask.repository.StudentRepository;
import com.elinext.testtask.repository.SubjectRepository;
import com.elinext.testtask.service.FacultyService;
import com.elinext.testtask.service.FacultyServiceImpl;
import com.elinext.testtask.service.StudentService;
import com.elinext.testtask.service.StudentServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    private static final FacultyService FACULTY_SERVICE = Mockito.mock(FacultyServiceImpl.class);
    private static final FacultyRepository FACULTY_REPOSITORY = Mockito.mock(FacultyRepository.class);
    private static final ScheduleRepository SCHEDULE_REPOSITORY = Mockito.mock(ScheduleRepository.class);
    private static final StudentRepository STUDENT_REPOSITORY = Mockito.mock(StudentRepository.class);
    private static final SubjectRepository SUBJECT_REPOSITORY = Mockito.mock(SubjectRepository.class);
    private static StudentService studentService;
    private static Faculty faculty;
    private static Student student;
    private static Schedule schedule;
    private static Subject subject;

    @BeforeClass
    public static void setUp() {
        faculty = new Faculty();
        subject = new Subject();
        student = new Student();
        schedule = new Schedule();
        student.setFaculty(faculty);
        student.setScheduleList(new ArrayList<>(Collections.singletonList(schedule)));
        faculty.setName("TestFaculty");
        student.setFirstName("TestFirstName");
        student.setSecondName("TestSecondName");
        schedule.setDay("Monday");
        subject.setName("Math");
        subject.setClassroom(123);
        studentService = new StudentServiceImpl(FACULTY_SERVICE, STUDENT_REPOSITORY, FACULTY_REPOSITORY, SCHEDULE_REPOSITORY, SUBJECT_REPOSITORY);
        schedule.setSubjects(new HashSet<>(Collections.singletonList(subject)));
        Mockito.when(STUDENT_REPOSITORY.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(student));
    }

    @Test
    public void updateTest() {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName("Andrew");
        studentDto.setSecondName("Waskovsky");
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setDay("Tuesday");
        scheduleDto.setScheduleId(123);
        scheduleDto.setSubjectIds(Collections.singletonList(123L));
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("UpdatedFaculty");
        Subject updatedSubject = new Subject();
        updatedSubject.setName("Physics");
        updatedSubject.setClassroom(213);
        studentDto.setScheduleDtoList(Collections.singletonList(scheduleDto));
        Mockito.when(FACULTY_SERVICE.readById(Mockito.any(Long.TYPE))).thenReturn(updatedFaculty);
        Mockito.when(SUBJECT_REPOSITORY.findById(Mockito.any(Long.TYPE))).thenReturn(Optional.of(updatedSubject));
        studentService.updateStudent(studentDto, 123);
        List<Subject> subjects = new ArrayList<>(student.getScheduleList().get(0).getSubjects());
        Assertions.assertEquals(student.getFirstName(), studentDto.getFirstName());
        Assertions.assertEquals(student.getSecondName(), studentDto.getSecondName());
        Assertions.assertEquals(student.getFaculty().getName(), updatedFaculty.getName());
        Assertions.assertEquals(student.getScheduleList().get(0).getDay(), scheduleDto.getDay());
        Assertions.assertEquals(subjects.get(0).getClassroom(), updatedSubject.getClassroom());
        Assertions.assertEquals(subjects.get(0).getName(), updatedSubject.getName());
    }

    @Test
    public void readTest() {
        StudentDto studentDto = studentService.readStudentDtoById(123);
        Assertions.assertEquals(studentDto.getFirstName(), student.getFirstName());
        Assertions.assertEquals(studentDto.getSecondName(), student.getSecondName());
        Assertions.assertEquals(studentDto.getScheduleDtoList().get(0).getDay(), schedule.getDay());
        List<SubjectDto> readiedSubjects = new ArrayList<>(studentDto.getScheduleDtoList().get(0).getSubjectDtoSet());
        Assertions.assertEquals(readiedSubjects.get(0).getClassroom(), subject.getClassroom());
        Assertions.assertEquals(readiedSubjects.get(0).getName(), subject.getName());
    }
}
