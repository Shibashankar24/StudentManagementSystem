package com.example.StudentCrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.StudentCrud.domain.Student;
import com.example.StudentCrud.service.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/test")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("students", studentService.listAll());
        return "index";
    }

    @GetMapping("/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new";
    }

    @GetMapping("/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.get(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "new";
        } else {
            return "redirect:/test/";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.delete(id);
        return "redirect:/test/";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        try {
            logger.info("Saving student: {}", student);
            studentService.save(student);
            return "redirect:/test/";
        } catch (Exception e) {
            logger.error("Error saving student", e);
            return "error"; // Return a custom error page if necessary
        }
    }
}
