package com.elinext.testtask.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;
    @OneToMany(mappedBy = "faculty")
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Faculty equalFaculty = (Faculty) obj;
        if(!equalFaculty.getStudents().containsAll(students) && !students.containsAll(equalFaculty.getStudents())){
            return false;
        }
        return equalFaculty.name.equals(name) && university.equals(equalFaculty.getUniversity());
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int multiplier = 25;
        int result = 1;
        result += multiplier*result + ((name == null ? 0 : name.hashCode()));
        result +=+ university.hashCode();
        if(students != null) {
            for (Student student : students) {
                result += student.hashCode();
            }
        }
        return result;
    }
}
