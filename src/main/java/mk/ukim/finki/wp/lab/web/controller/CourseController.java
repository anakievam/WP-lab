package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    private final TeacherService teacherService;

    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping()
    public String getCoursesPage(@RequestParam(required = false) String error, Model model){
        if (error!=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses=courseService.listAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) Long courseId, @RequestParam String name, @RequestParam String description, @RequestParam long teacherId){
        if(courseId != null)
        {
            Course course = courseService.findById(courseId);
            Course tmpCourse = courseService.findByName(name);
            if(tmpCourse != null && !tmpCourse.getCourseId().equals(courseId))
                    return "redirect:/courses?error=You can't rename course to an already existing name course.";
            course.setName(name);
            course.setDescription(description);
            course.setTeacher(teacherService.findById(teacherId));
        }
        else {
            Course course = courseService.save(name, description, teacherId);
            if (course == null){
                return "redirect:/courses?error=You can't add a course with a name that already exists.";
            }
        }
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id){
        this.courseService.deleteByid(id);
        return "redirect:/courses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model){
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model){
        if (courseService.findById(id) != null) {
            Course course = courseService.findById(id);
            List<Teacher> teachers = teacherService.findAll();
            model.addAttribute("teachers", teachers);
            model.addAttribute("course", course);
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";
    }
}
