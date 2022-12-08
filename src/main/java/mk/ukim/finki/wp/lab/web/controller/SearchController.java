package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final CourseService courseService;

    public SearchController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getSearchPage(@RequestParam(required = false) String error, Model model){
        if (error!=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "searchAll";
    }

    @PostMapping
    public String searchAll(@RequestParam String text, Model model){
        if (text == null || text.isEmpty()){
            return "redirect:/search?error=You have to enter something.";
        }
        String result = courseService.search(text);
        if (!result.isEmpty()){
            model.addAttribute("foundSmth", true);
            model.addAttribute("foundString", result);
            return "searchAll";
        }else{
            return "redirect:/search?error=No match was found.";
        }
    }
}
