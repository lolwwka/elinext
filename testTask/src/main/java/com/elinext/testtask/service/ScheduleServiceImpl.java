package com.elinext.testtask.service;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.dto.StudentDto;
import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Schedule;
import com.elinext.testtask.entity.Student;
import com.elinext.testtask.entity.Subject;
import com.elinext.testtask.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final StudentService studentService;
    private final ScheduleRepository scheduleRepository;
    private SubjectService subjectService;

    public ScheduleServiceImpl(StudentService studentService, ScheduleRepository scheduleRepository) {
        this.studentService = studentService;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public boolean create(ScheduleDto scheduleDto) {
        Student student = studentService.readById(scheduleDto.getStudentId());
        if(student == null){
            return false;
        }
        for(Schedule schedule : student.getScheduleList()){
            if(schedule.getDay().equals(scheduleDto.getDay())){
                student.getScheduleList().remove(schedule);
                scheduleRepository.deleteById(schedule.getId());
                break;
            }
        }
        List<Subject> subjectList = new ArrayList<>();
        insertSubjects(scheduleDto, subjectList);
        Schedule schedule = new Schedule();
        schedule.setDay(scheduleDto.getDay());
        schedule.setStudent(student);
        schedule.setSubjects(new HashSet<>(subjectList));
        scheduleRepository.save(schedule);
        return true;
    }

    @Override
    public boolean updateSchedule(long scheduleId, ScheduleDto scheduleDto) {
        Schedule schedule = readById(scheduleId);
        if(schedule == null){
            return false;
        }
        if(schedule.getDay() != null) schedule.setDay(scheduleDto.getDay());
        schedule.setStudent(studentService.readById(scheduleDto.getStudentId()));
        List<Subject> subjectList = new ArrayList<>();
        insertSubjects(scheduleDto, subjectList);
        schedule.setSubjects(new HashSet<>(subjectList));
        return true;
    }

    @Override
    public Schedule readById(long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElse(null);
    }

    @Override
    public boolean deleteById(long id) {
        Schedule schedule = readById(id);
        if(schedule == null){
            return false;
        }
        scheduleRepository.deleteById(id);
        return true;
    }

    @Override
    public ScheduleDto getScheduleInfo(long id) {
        Schedule schedule = readById(id);
        if(schedule == null){
            return new ScheduleDto(true);
        }
        return convertScheduleToDto(schedule);
    }

    private void insertSubjects(ScheduleDto scheduleDto, List<Subject> subjectList) {
        if (scheduleDto.getSubjectIds() != null) {
            for (Long num : scheduleDto.getSubjectIds()) {
                Subject subject = subjectService.getSubjectById(num);
                if(subject == null) continue;
                subjectList.add(subject);
            }
        }
    }
    private ScheduleDto convertScheduleToDto(Schedule schedule){
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setDay(schedule.getDay());
        StudentDto studentDto = new StudentDto();
        Student student = schedule.getStudent();
        if(student != null) {
            studentDto.setFirstName(student.getFirstName());
            studentDto.setSecondName(student.getSecondName());
            studentDto.setFacultyId(student.getFaculty().getId());
            scheduleDto.setStudentDto(studentDto);
        }
        Set<SubjectDto> subjectDtoSet = new HashSet<>();
        for(Subject subject : schedule.getSubjects()){
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setName(subject.getName());
            subjectDto.setClassroom(subject.getClassroom());
            subjectDtoSet.add(subjectDto);
        }
        scheduleDto.setSubjectDtoSet(subjectDtoSet);
        return scheduleDto;
    }
    @Autowired
    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
