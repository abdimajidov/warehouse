package uz.sardorbek.warehouse.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import uz.sardorbek.warehouse.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductController {


    ProductService productService;

    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @PostMapping("/get")
    public String searchProducts(Model model, HttpServletRequest request) {
        return productService.searchProducts(model, request);
    }

    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @GetMapping("/get")
    public String getProducts(Model model) {
        return productService.getProducts(model);
    }



    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @GetMapping("/add")
    public String getAddProducts() {
        return "add";
    }


    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @SneakyThrows
    @PostMapping("/add")
    public String addProduct(HttpServletRequest request) {
        return productService.addProduct(request);
    }



    @PreAuthorize(value = "hasAuthority('DIRECTOR')")
    @GetMapping("/delete")
    public String getDeleteProducts(Model model) {

        return productService.getDeleteProducts(model);

    }


    @PreAuthorize(value = "hasAuthority('DIRECTOR')")
    @GetMapping("/delete/byName")
    public String deleteProduct(@RequestParam("name") String name) {
        return productService.deleteProduct(name);
    }

    @PreAuthorize(value = "hasAuthority('DIRECTOR') or hasAuthority('USER')")
    @GetMapping("/get/file")
    public void getFile(@RequestParam("type") String type, HttpServletResponse response) {
        productService.getFile(type, response);
    }


}

