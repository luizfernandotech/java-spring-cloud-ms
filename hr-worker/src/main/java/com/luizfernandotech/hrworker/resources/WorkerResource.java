package com.luizfernandotech.hrworker.resources;

import com.luizfernandotech.hrworker.entities.Worker;
import com.luizfernandotech.hrworker.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

    @Autowired
    private Environment environment;

    @Autowired
    private WorkerRepository repository;

    @GetMapping
    public List<Worker> findAll() {

        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Worker findById(@PathVariable Long id) {

        logger.info("PORT = " + environment.getProperty("local.server.port"));

        return repository.findById(id).get();
    }
}
