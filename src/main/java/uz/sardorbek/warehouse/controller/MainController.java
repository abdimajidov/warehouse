package uz.sardorbek.warehouse.controller;



import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class MainController {

    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @GetMapping
    public String main(){
        return "index";
    }
}
