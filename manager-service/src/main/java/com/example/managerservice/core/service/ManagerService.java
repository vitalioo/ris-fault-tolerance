package com.example.managerservice.core.service;

import com.example.managerservice.core.model.request.HashCrackRequest;
import com.example.managerservice.core.model.request.HashStatusRequest;
import com.example.managerservice.core.model.response.HashCrackResponse;
import com.example.managerservice.core.model.response.HashStatusResponse;

public interface ManagerService {
    HashCrackResponse createTask(HashCrackRequest request);

    HashStatusResponse getHashStatus(HashStatusRequest request);

    void updateTaskData(String requestId, String data);

    void updateTaskStatus(String requestId, String status);

    Integer getCountOfCompletedWorkers(String requestId);
}
