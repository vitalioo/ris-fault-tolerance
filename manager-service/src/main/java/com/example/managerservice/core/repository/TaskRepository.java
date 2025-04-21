package com.example.managerservice.core.repository;

import com.example.managerservice.core.entity.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    Task findByRequestId(String requestId);

    List<Task> findAllByStatus(String status);
}
