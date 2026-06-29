package com.techouts.productController.service;

import com.techouts.productController.dao.ProductRepo;
import com.techouts.productController.model.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

//    @Autowired
//    CartRepo cartRepo;

    public List<Products> findAll() {
        return productRepo.findAll();
    }

    public Products findById(int id) {
        return productRepo.findById(id).orElseThrow();
    }

}
