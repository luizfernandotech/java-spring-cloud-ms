package com.luizfernandotech.hrpayroll.services;

import com.luizfernandotech.hrpayroll.entities.Payment;
import com.luizfernandotech.hrpayroll.entities.Worker;
import com.luizfernandotech.hrpayroll.feignclients.WorkerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private WorkerFeignClient workerFeignClient;

    public Payment getPayment(Long workerId, int days) {

        Worker worker = workerFeignClient.findById(workerId);

        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }
}
