package com.luizfernandotech.hrworker.repositories;

import com.luizfernandotech.hrworker.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
