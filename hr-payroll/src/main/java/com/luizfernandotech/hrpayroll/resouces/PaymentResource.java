package com.luizfernandotech.hrpayroll.resouces;

import com.luizfernandotech.hrpayroll.entities.Payment;
import com.luizfernandotech.hrpayroll.services.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {

    @Autowired
    private PaymentService service;

    @HystrixCommand(fallbackMethod = "getPaymentAlternative")
    @GetMapping(value = "/{workerId}/days/{days}")
    public Payment getPayment(@PathVariable Long workerId, @PathVariable Integer days) {

        return service.getPayment(workerId, days);
    }

    public Payment getPaymentAlternative(Long workerId, Integer days) {
        return new Payment("Bran", 400.0, days);
    }
}
