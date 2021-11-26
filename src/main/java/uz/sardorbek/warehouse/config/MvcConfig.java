package uz.sardorbek.warehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index");
        registry.addViewController("/product/add").setViewName("add");
        registry.addViewController("/product/get").setViewName("get");
        registry.addViewController("/product/delete").setViewName("delete");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations("/static/","classpath:/static/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/","classpath:/resources/");
        registry.addResourceHandler("/templates/**").addResourceLocations("/templates/","classpath:/templates/");
    }
}
