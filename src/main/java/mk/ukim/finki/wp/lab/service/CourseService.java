package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;
import java.util.Optional;

public interface CourseService{

    List<Student> listStudentsByCourse(Long courseId);

    Course addStudentInCourse(String username, Long courseId);

    List<Course> listAll();

    Course findById(long id);

    Course save(String name, String desc, long teacherId);

    void deleteByid(long id);

    Course findByName(String name);

    String search(String text);
}
