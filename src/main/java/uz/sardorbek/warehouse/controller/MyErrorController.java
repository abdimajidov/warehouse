package uz.sardorbek.warehouse.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status!=null){
            Integer statusCode = Integer.parseInt(status.toString());

            if(statusCode== HttpStatus.FORBIDDEN.value()){
                return "/error/403";
            }
        }
        return "/error/some";
    }

}
