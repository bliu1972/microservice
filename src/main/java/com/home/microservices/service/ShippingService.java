package com.home.microservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.home.microservices.repository.Shipping;
import com.home.microservices.repository.ShippingPriceRepository;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "https://bliu1972.github.io", maxAge = 3600)
public class ShippingService {
    @Autowired
    private ShippingPriceRepository shippingPriceRepository;
    static Shipping shipping = new Shipping();

    @GetMapping("/shipping")
    public List<Shipping> getShipping() {
        List<Shipping> shippingList = new ArrayList<>();

        Iterable<Shipping> shippingIter = shippingPriceRepository.findAll();
        shippingIter.forEach((sp) -> {
            Shipping shippign = new Shipping();
            shippign.type = sp.type;
            shippign.price = sp.price;
            shippingList.add(shippign);
        });

        return shippingList;
    }
    
}
