package com.elinext.testtask.service;

import com.elinext.testtask.dto.ScheduleDto;
import com.elinext.testtask.dto.SubjectDto;
import com.elinext.testtask.entity.Schedule;
import com.elinext.testtask.entity.Subject;
import com.elinext.testtask.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SubjectServiceImpl implements SubjectService{
    private final ScheduleService scheduleService;
    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(ScheduleService scheduleService, SubjectRepository subjectRepository) {
        this.scheduleService = scheduleService;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public boolean createSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findByNameAndClassroom(subjectDto.getName(), subjectDto.getClassroom());
        Schedule schedule = scheduleService.readById(subjectDto.getScheduleId());
        if(subject != null && subject.getClassroom() == subjectDto.getClassroom() || schedule == null){
            return false;
        }
        subject = new Subject();
        subject.setClassroom(subjectDto.getClassroom());
        subject.setName(subjectDto.getName());
        Set<Subject> subjectSet = schedule.getSubjects();
        subjectSet.add(subject);
        schedule.setSubjects(subjectSet);
        subjectRepository.save(subject);
        return true;
    }

    @Override
    public boolean updateSubject(long subjectId, SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->
                new IllegalArgumentException("Subject id don't exist"));
        Schedule schedule = scheduleService.readById(subjectDto.getScheduleId());
        if(schedule == null){
            return false;
        }
        subject.setName(subjectDto.getName());
        subject.setClassroom(subjectDto.getClassroom());
        List<Schedule> scheduleList = subject.getScheduleList();
        scheduleList.add(schedule);
        Set<Subject> subjectSet = schedule.getSubjects();
        subjectSet.add(subject);
        schedule.setSubjects(subjectSet);
        return true;
    }

    @Override
    public boolean deleteSubjectById(long id) {
        if(subjectRepository.findById(id).orElse(null) == null){
            return false;
        }
        subjectRepository.deleteById(id);
        return true;
    }

    @Override
    public SubjectDto getSubjectInfo(long id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Subject id don't exist"));
        return convertSubjectToDto(subject);
    }

    @Override
    public Subject getSubjectById(long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subject id don't exist"));
    }

    private SubjectDto convertSubjectToDto(Subject subject){
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setClassroom(subject.getClassroom());
        subjectDto.setName(subject.getName());
        List<ScheduleDto> scheduleList = new ArrayList<>();
        for(Schedule schedule : subject.getScheduleList()){
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setDay(schedule.getDay());
            scheduleDto.setStudentId(schedule.getStudent().getId());
            scheduleList.add(scheduleDto);
        }
        subjectDto.setScheduleDtoList(scheduleList);
        return subjectDto;
    }
}
