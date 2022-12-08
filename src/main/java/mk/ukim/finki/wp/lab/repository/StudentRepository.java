package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    public static List<Student> students=new ArrayList<>();

    @PostConstruct
    public void init(){
        students.add(new Student("marija.anakieva", "ma", "Marija", "Anakieva"));
        students.add(new Student("petre.petrev", "pp", "Petre", "Petrev"));
        students.add(new Student("trajche.trajchev", "tt", "Trajche", "Trajchev"));
        students.add(new Student("ana.aneva", "aa", "Ana", "Aneva"));
        students.add(new Student("petko.petkov", "pp", "Petko", "Petkov"));
    }

    public List<Student> findAllStudents(){
        return students;
    }

    public List<Student> findAllByNameOrSurname(String text){
        return students.stream().filter(student -> student.getName().contains(text) || student.getSurname().contains(text))
                .collect(Collectors.toList());
    }

    public Student findStudent(String text){
        for (Student student : students){
            String name = student.getName().toLowerCase();
            String surname = student.getSurname().toLowerCase();
            String username = student.getUsername().toLowerCase();
            if (name.contains(text.toLowerCase()) || surname.contains(text.toLowerCase()) ||
                    username.contains(text.toLowerCase()))
                return student;
        }
        return null;
    }
}
