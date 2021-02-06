package com.luizfernandotech.hrworker.resources;

import com.luizfernandotech.hrworker.entities.Worker;
import com.luizfernandotech.hrworker.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

    private static Logger logger = LoggerFactory.getLogger(WorkerResource.class);

    @Value("${test.config}")
    private String testConfig;

    @Autowired
    private Environment environment;

    @Autowired
    private WorkerRepository repository;

    @GetMapping(value = "/configs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getConfigs() {
        logger.info("CONFIG = " + testConfig);
    }

    @GetMapping
    public List<Worker> findAll() {

        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Worker findById(@PathVariable Long id) {

//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        logger.info("PORT = " + environment.getProperty("local.server.port"));

        return repository.findById(id).get();
    }
}
