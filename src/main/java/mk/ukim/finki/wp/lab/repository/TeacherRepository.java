package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherRepository {

    List<Teacher> teachers=new ArrayList<>();

    @PostConstruct
    public void init(){
        teachers.add(new Teacher("Dzvonko", "Dzvonkov"));
        teachers.add(new Teacher("Sara", "Sareva"));
        teachers.add(new Teacher("Mirche", "Mirchev"));
        teachers.add(new Teacher( "Kosta", "Kostov"));
        teachers.add(new Teacher("Gligor", "Gligorov"));
    }

    public List<Teacher> findAll(){
        return teachers;
    }

    public Teacher findById(long id){
        for (Teacher teacher : teachers){
            if (teacher.getId().equals(id))
                return teacher;
        }
        return null;
    }
}
