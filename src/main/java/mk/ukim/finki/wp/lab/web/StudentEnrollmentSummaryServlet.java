package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="StudentEnrollmentSummary", urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummaryServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;

    private final CourseService courseService;

    public StudentEnrollmentSummaryServlet(SpringTemplateEngine springTemplateEngine, CourseService courseService) {
        this.springTemplateEngine = springTemplateEngine;
        this.courseService = courseService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long courseId=Long.parseLong((String) req.getSession().getAttribute("courseId"));
        String username=req.getParameter("username");
        if (username != null)
            courseService.addStudentInCourse(username, courseId);
        WebContext context=new WebContext(req, resp, req.getServletContext());
        context.setVariable("studentsInCourse", courseService.listStudentsByCourse(courseId));
        String name="";
        for (Course course : courseService.listAll()){
            if (course.getCourseId().equals(courseId))
                name=course.getName();
        }
        context.setVariable("nameOfCourse", name);
        req.getSession().invalidate();
        springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }
}
