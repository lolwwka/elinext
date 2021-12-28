package com.elinext.testtask.entity;


import javax.persistence.*;
import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "university")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    @OneToMany(mappedBy = "university")
    private Set<Faculty> faculties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    public void setId(long id) {
        this.id = id;
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
        University equalUniversity = (University) obj;
        if (!faculties.containsAll(equalUniversity.getFaculties()) &&
                !equalUniversity.getFaculties().containsAll(faculties)){
            return false;
        }
        return name.equals(equalUniversity.name);
    }

    @Override
    public int hashCode() {
        final int multiplier = 25;
        int result = 1;
        result = multiplier*result + ((name == null) ? 0 : name.hashCode());
        if(faculties != null) {
            for (Faculty faculty : faculties) {
                result += faculty.hashCode();
            }
        }
        return result;
    }
}
