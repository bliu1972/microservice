package com.home.microservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "https://bliu1972.github.io", maxAge = 3600)
public class ShippingService {
    static Shippinng shipping = new Shippinng();

    @GetMapping("/shipping")
    public List<Shippinng> getShipping() {
        List<Shippinng> shippingList = new ArrayList<>();

        Shippinng shippign1 = new Shippinng();
        shippign1.type = "Overnight";
        shippign1.price = 25.99;
        shippingList.add(shippign1);

        Shippinng shippign2 = new Shippinng();
        shippign2.type = "2-Day";
        shippign2.price = 9.99;
        shippingList.add(shippign2);

        Shippinng shippign3 = new Shippinng();
        shippign3.type = "Postal";
        shippign3.price = 2.99;
        shippingList.add(shippign3);

        return shippingList;
    }
    
}
