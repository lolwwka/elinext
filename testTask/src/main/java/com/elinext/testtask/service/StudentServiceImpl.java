package com.elinext.testtask.service;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {
    private final FacultyService facultyService;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final ScheduleRepository scheduleRepository;
    private final SubjectRepository subjectRepository;

    public StudentServiceImpl(FacultyService facultyService, StudentRepository studentRepository, FacultyRepository facultyRepository, ScheduleRepository scheduleRepository, SubjectRepository subjectRepository) {
        this.facultyService = facultyService;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public boolean createStudent(StudentDto studentDto) {
        Faculty faculty = facultyRepository.findById(studentDto.getFacultyId()).orElse(null);
        if (faculty == null) {
            return false;
        }
        for (Student student : faculty.getStudents()) {
            if (student.getFirstName().equals(studentDto.getFirstName()) &&
                    student.getSecondName().equals(studentDto.getSecondName())) {
                return false;
            }
        }
        Student student = new Student();
        student.setFaculty(facultyService.readById(studentDto.getFacultyId()));
        student.setFirstName(studentDto.getFirstName());
        student.setSecondName(studentDto.getSecondName());
        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean deleteStudent(long id) {
        studentRepository.deleteById(id);
        return studentRepository.findById(id).isEmpty();
    }

    @Override
    public Student readById(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateStudent(StudentDto studentDto, long id) {
        Student student = readById(id);
        student.setFirstName(studentDto.getFirstName());
        student.setSecondName(studentDto.getSecondName());
        Faculty faculty = facultyService.readById(studentDto.getFacultyId());
        if(faculty != null) student.setFaculty(faculty);
        List<Schedule> schedules = student.getScheduleList();
        schedules.forEach(scheduleRepository::delete);
        schedules.clear();
        for(ScheduleDto scheduleDto : studentDto.getScheduleDtoList()){
            Schedule schedule = new Schedule();
            Set<Subject> subjects = new HashSet<>();
            schedule.setDay(scheduleDto.getDay());
            scheduleDto.getSubjectIds().forEach(subjectId -> subjects.add(subjectRepository.findById(subjectId).orElse(null)));
            schedule.setSubjects(subjects);
            schedule.setStudent(student);
            schedules.add(schedule);
        }
        student.setScheduleList(schedules);
        return true;
    }

    @Override
    public StudentDto readStudentDtoById(long id) {
        Student student = readById(id);
        if (student == null) {
            StudentDto studentDto = new StudentDto();
            studentDto.setError(true);
            return studentDto;
        }
        return convertStudentToDto(student);
    }

    private StudentDto convertStudentToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(student.getFirstName());
        studentDto.setSecondName(student.getSecondName());
        studentDto.setFacultyId(student.getFaculty().getId());
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        for(Schedule schedule : student.getScheduleList()){
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setDay(schedule.getDay());
            Set<SubjectDto> subjectDtoSet = new HashSet<>();
            for(Subject subject : schedule.getSubjects()){
                SubjectDto subjectDto = new SubjectDto();
                subjectDto.setClassroom(subject.getClassroom());
                subjectDto.setName(subject.getName());
                subjectDtoSet.add(subjectDto);
            }
            scheduleDto.setSubjectDtoSet(subjectDtoSet);
            scheduleDtoList.add(scheduleDto);
        }
        studentDto.setScheduleDtoList(scheduleDtoList);
        return studentDto;
    }
}
