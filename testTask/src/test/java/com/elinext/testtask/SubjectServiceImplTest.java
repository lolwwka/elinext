package com.elinext.testtask;

import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Schedule;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.Subject;
import com.elinext.testtask.repository.SubjectRepository;
import com.elinext.testtask.service.ScheduleService;
import com.elinext.testtask.service.ScheduleServiceImpl;
import com.elinext.testtask.service.SubjectService;
import com.elinext.testtask.service.SubjectServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceImplTest {
    private static final ScheduleService SCHEDULE_SERVICE = Mockito.mock(ScheduleServiceImpl.class);
    private static final SubjectRepository SUBJECT_REPOSITORY = Mockito.mock(SubjectRepository.class);
    private static SubjectService subjectService;
    private static Subject subject;
    private static Schedule schedule;
    private static Student student;
    @BeforeClass
    public static void setUp(){
        subject = new Subject();
        schedule = new Schedule();
        student = new Student();
        subject.setName("TestSubject");
        subject.setClassroom(123);
        schedule.setDay("Monday");
        student.setId(321312);
        schedule.setStudent(student);
        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule);
        subject.setScheduleList(scheduleList);
        Set<Subject> subjectSet = new HashSet<>();
        subjectSet.add(subject);
        schedule.setSubjects(subjectSet);
        subjectService = new SubjectServiceImpl(SCHEDULE_SERVICE, SUBJECT_REPOSITORY);
        Mockito.when(SUBJECT_REPOSITORY.findById(Mockito.any(Long.class))).thenReturn(Optional.of(subject));
        Mockito.when(SCHEDULE_SERVICE.readById(Mockito.any(Long.class))).thenReturn(schedule);
    }
    @Test
    public void updateTest(){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setScheduleId(213);
        subjectDto.setName("Math");
        subjectDto.setClassroom(213);
        subjectService.updateSubject(1, subjectDto);
        Assertions.assertEquals(subject.getName(), subjectDto.getName());
        Assertions.assertEquals(subject.getClassroom(), subjectDto.getClassroom());
        Assertions.assertEquals(subject.getScheduleList().get(0).getDay(), schedule.getDay());
    }
    @Test
    public void readTest(){
        SubjectDto subjectDto = subjectService.getSubjectInfo(123);
        Assertions.assertEquals(subjectDto.getClassroom(), subject.getClassroom());
        Assertions.assertEquals(subjectDto.getName(), subject.getName());
        Assertions.assertEquals(subjectDto.getScheduleDtoList().get(0).getDay(), schedule.getDay());
        Assertions.assertEquals(student.getId(), subjectDto.getScheduleDtoList().get(0).getStudentId());
    }
}
