package hex.multinode.visualizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("color", "red");
        model.addAttribute("title", "Lorem ipsum dolor");
        model.addAttribute("content", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        return "home.html";
    }
}
