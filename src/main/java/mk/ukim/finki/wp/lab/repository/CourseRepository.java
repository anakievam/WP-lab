package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class CourseRepository {

    public static List<Course> courses = new ArrayList<>();

    @PostConstruct
    public void init(){
        List<Student> list=new ArrayList<>();
        list.add(new Student("marija.anakieva", "ma", "Marija", "Anakieva"));
        list.add(new Student("petre.petrev", "pp", "Petre", "Petrev"));
        Teacher teacher = new Teacher( "Dzvonko", "Dzvonkov");
        courses.add(new Course("Web Programming", "web programming description", list, teacher));
        courses.add(new Course("Databases", "databases description", list, teacher));
        courses.add(new Course("Advanced Programming", "advanced programming description", list, teacher));
        courses.add(new Course("Linear algebra and applications", "linear algebra description", list, teacher));
        courses.add(new Course("Parallel and distributed processing", "parallel and distributed description", list, teacher));
    }

    public List<Course> findAllCourses(){
        courses.sort(Comparator.comparing(Course::getName));
        return courses;
    }

    public Course findById(Long courseId){
        for (Course course : courses){
            if (course.getCourseId().equals(courseId))
                return course;
        }
        return null;
    }

    public Course findByName(String name){
        for (Course course : courses){
            if (course.getName().equals(name))
                return course;
        }
        return null;
    }

    public List<Student> findAllStudentsByCourse(Long courseId){
        return courses.stream().filter(course -> course.getCourseId().equals(courseId)).findFirst().get().getStudents();
    }

    public Course addStudentToCourse(Student student, Course course){
        for(Student s : course.getStudents()){
            if (s.getName().equals(student.getName()) && s.getSurname().equals(student.getSurname()))
                return course;
        }
        List<Student> list = new ArrayList<>(course.getStudents());
        list.add(student);
        course.setStudents(list);
        return course;
    }

    public Course save(String name, String desc, Teacher teacher){
        Course course = this.findByName(name);
        if (course != null)
            return null;
        List<Student> students=new ArrayList<>();
        course = new Course(name, desc, students, teacher);
        courses.add(course);
        return course;
    }

    public void delete(long id){
        courses.removeIf(course -> course.getCourseId().equals(id));
    }
}
