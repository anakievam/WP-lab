package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ListStudentServlet", urlPatterns="/AddStudent")
public class ListStudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("students", studentService.listAll());
        springTemplateEngine.process("listStudents.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String text=req.getParameter("text");
        Student student=null;
        if (text!=null && !text.isEmpty()){
            student = studentService.find(text);
        }
        if (student!=null){
            req.getSession().setAttribute("studentName", student.getName());
            req.getSession().setAttribute("studentSurname", student.getSurname());
            req.getSession().setAttribute("studentUsername", student.getUsername());
            req.getSession().setAttribute("found", true);
            resp.sendRedirect("AddStudent");
        }
        else{
            req.getSession().setAttribute("found", false);
        }

        String chosenCourse = req.getParameter("courseId");
        if (chosenCourse != null)
            req.getSession().setAttribute("courseId", chosenCourse);
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("students", studentService.listAll());
        springTemplateEngine.process("listStudents.html", context, resp.getWriter());
    }
}
