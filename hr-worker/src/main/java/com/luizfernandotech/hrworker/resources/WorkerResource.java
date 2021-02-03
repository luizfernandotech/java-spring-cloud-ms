package com.luizfernandotech.hrworker.resources;

import com.luizfernandotech.hrworker.entities.Worker;
import com.luizfernandotech.hrworker.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    @Autowired
    private WorkerRepository repository;

    @GetMapping
    public List<Worker> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Worker findById(@PathVariable Long id) {
        return repository.findById(id).get();
    }
}
