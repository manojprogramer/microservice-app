package com.techouts.productController.controller;

import com.techouts.productController.model.Products;
import com.techouts.productController.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://localhost:3000"})
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping({"/", "/getproducts"})
    private List<Products> getProducts() {
        List<Products> products =  productService.findAll();
        System.out.println(products);
        return products;
    }

    @GetMapping("/getproducts/{id}")
    private Products getProduct(@PathVariable int id) {
        return productService.findById(id);
    }


}
