package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.TeacherNotFoundException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentService studentService;

    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Student student=null;
        for (Student s : studentService.listAll()){
            if (s.getUsername().equals(username))
                student=s;
        }
        Course course = courseRepository.findById(courseId);
        return courseRepository.addStudentToCourse(student, course);
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Course findById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(String name, String desc, long teacherId) {
        //Teacher teacher=teacherRepository.findById(teacherId).orElseThrow(() ->new TeacherNotFoundException(teacherId));
        Teacher teacher = teacherRepository.findById(teacherId);
        if (teacher != null)
             return this.courseRepository.save(name, desc, teacher);
        return null;
    }

    @Override
    public void deleteByid(long id) {
        this.courseRepository.delete(id);
    }

    @Override
    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public String search(String text) {
        StringBuilder sb=new StringBuilder();
        text=text.toLowerCase();
        for (Student student : studentService.listAll()){
            if (student.getName().toLowerCase().contains(text) ||
                    student.getSurname().toLowerCase().contains(text) ||
                    student.getUsername().toLowerCase().contains(text)){
                sb.append("Found student: ");
                sb.append(student.getName()).append(" ").append(student.getSurname()).append("\n");
            }
        }

        for(Course course : courseRepository.findAllCourses()){
            if (course.getName().toLowerCase().contains(text)){
                sb.append("Found course: ");
                sb.append(course.getName()).append(" ").append(course.getDescription());
            }
        }

        return sb.toString();
    }

}
