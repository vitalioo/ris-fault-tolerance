package com.example.workerservice.service;

import com.example.workerservice.model.CrackHashManagerRequest;

public interface WorkerService {
    void consumeTask(CrackHashManagerRequest request);
}
