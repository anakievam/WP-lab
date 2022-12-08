package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Student;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static mk.ukim.finki.wp.lab.repository.StudentRepository.students;

@WebServlet(name="CreateStudentServlet", urlPatterns = "/createStudent")
public class CreateStudentServlet extends HttpServlet {

    private final SpringTemplateEngine springTemplateEngine;

    public CreateStudentServlet(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("createStudent", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String name=req.getParameter("name");
        String surname=req.getParameter("surname");
        Student student=new Student(username, password, name, surname);
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()
                && name != null && !name.isEmpty() && surname != null && !surname.isEmpty()) {
            students.add(student);
            resp.sendRedirect("/AddStudent");
        }else{
            req.getSession().setAttribute("createStudentError", true);
            resp.sendRedirect("/createStudent");
        }
    }
}
