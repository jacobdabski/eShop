package com.company;

import com.company.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private ProductService service;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);



    }

}
