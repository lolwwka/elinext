package com.elinext.testtask;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Faculty;
import com.elinext.testtask.entity.Schedule;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.Subject;
import com.elinext.testtask.repository.ScheduleRepository;
import com.elinext.testtask.service.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {
    private static final StudentService STUDENT_SERVICE = Mockito.mock(StudentServiceImpl.class);
    private static final ScheduleRepository SCHEDULE_REPOSITORY = Mockito.mock(ScheduleRepository.class);
    private static final SubjectService SUBJECT_SERVICE = Mockito.mock(SubjectServiceImpl.class);
    private static ScheduleServiceImpl scheduleService;
    private static Student student;
    private static Schedule schedule;
    private static Subject subject;
    private static Faculty faculty;

    @BeforeClass
    public static void setUp(){
        student = new Student();
        schedule = new Schedule();
        subject = new Subject();
        faculty = new Faculty();
        student.setScheduleList(Collections.singletonList(schedule));
        student.setFirstName("TestFirstName");
        student.setSecondName("TestSecondName");
        student.setFaculty(faculty);
        subject.setName("TestSubject");
        subject.setClassroom(123);
        schedule.setDay("Monday");
        schedule.setSubjects(Collections.singleton(subject));
        schedule.setStudent(student);
        faculty.setName("TestFaculty");
        faculty.setId(321312);
        scheduleService = new ScheduleServiceImpl(STUDENT_SERVICE, SCHEDULE_REPOSITORY);
        scheduleService.setSubjectService(SUBJECT_SERVICE);
        Mockito.when(STUDENT_SERVICE.readById(Mockito.any(Long.class))).thenReturn(student);
        Mockito.when(SCHEDULE_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(schedule));
        Mockito.when(SUBJECT_SERVICE.getSubjectById(Mockito.any(Long.class))).thenReturn(subject);
    }
    @Test
    public void updateScheduleTest(){
        ScheduleDto scheduleDto = new ScheduleDto();
        Student updatedStudent = new Student();
        Subject updatedSubject = new Subject();
        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setDay("Tuesday");
        scheduleDto.setStudentId(213);
        updatedStudent.setFirstName("Andrew");
        updatedStudent.setSecondName("Waskovsky");
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(updatedSchedule);
        updatedStudent.setScheduleList(scheduleList);
        scheduleDto.setDay("Tuesday");
        scheduleDto.setSubjectIds(Collections.singletonList(123L));
        updatedSubject.setName("Math");
        Mockito.when(STUDENT_SERVICE.readById(Mockito.any(Long.class))).thenReturn(updatedStudent);
        Mockito.when(SUBJECT_SERVICE.getSubjectById(Mockito.any(Long.class))).thenReturn(updatedSubject);
        scheduleService.updateSchedule(123,scheduleDto);
        List<Subject> subjectList = new ArrayList<>(schedule.getSubjects());
        Assertions.assertEquals(schedule.getStudent().getFirstName(), updatedStudent.getFirstName());
        Assertions.assertEquals(schedule.getStudent().getSecondName(), updatedStudent.getSecondName());
        Assertions.assertEquals(subjectList.get(0).getName(), updatedSubject.getName());
        Assertions.assertEquals(schedule.getDay(), scheduleDto.getDay());
    }
    @Test
    public void readScheduleTest(){
        ScheduleDto scheduleDto = scheduleService.getScheduleInfo(123);
        List<SubjectDto> dtoSubjects = new ArrayList<>(scheduleDto.getSubjectDtoSet());
        Assertions.assertEquals(scheduleDto.getDay(), schedule.getDay());
        Assertions.assertEquals(scheduleDto.getStudentDto().getFirstName(), student.getFirstName());
        Assertions.assertEquals(scheduleDto.getStudentDto().getSecondName(), student.getSecondName());
        Assertions.assertEquals(dtoSubjects.get(0).getName(), subject.getName());
        Assertions.assertEquals(scheduleDto.getStudentDto().getFacultyId(), faculty.getId());
        Assertions.assertSame(dtoSubjects.get(0).getClassroom(), subject.getClassroom());
    }
}
