package com.example.managerservice.core.service.impl;

import com.example.common.model.Status;
import com.example.managerservice.core.entity.Task;
import com.example.managerservice.core.model.request.HashCrackRequest;
import com.example.managerservice.core.model.request.HashStatusRequest;
import com.example.managerservice.core.model.response.HashCrackResponse;
import com.example.managerservice.core.model.response.HashStatusResponse;
import com.example.managerservice.core.repository.TaskRepository;
import com.example.managerservice.core.service.ManagerService;
import com.example.managerservice.core.service.TaskSenderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final TaskRepository taskRepository;
    private final TaskSenderService taskService;

    @Override
    public HashCrackResponse createTask(HashCrackRequest request) {
        String requestId = UUID.randomUUID().toString();
        Task task = Task.builder()
                .requestId(requestId)
                .hash(request.hash())
                .maxLength(request.maxLength())
                .createdAt(LocalDateTime.now())
                .build();

        taskRepository.save(task);
        taskService.sendTask(task);
        log.info("Created and send task with ID {}", requestId);

        return new HashCrackResponse(requestId);
    }

    @Override
    public HashStatusResponse getHashStatus(HashStatusRequest request) {
        Task task = taskRepository.findByRequestId(request.requestId());
        if (task == null) {
            return new HashStatusResponse("", null);
        }

        String status = task.getStatus();
        String data = task.getData();
        if (data != null) {
            data = data.trim();
        }

        if (Status.READY.equals(status) || Status.PARTITION_READY.equals(status)) {
            return new HashStatusResponse(status, data);
        } else {
            return new HashStatusResponse(status, null);
        }
    }

    @Override
    @Transactional
    public void updateTaskData(String requestId, String data) {
        Task task = taskRepository.findByRequestId(requestId);
        String currentData = task.getData();
        if (currentData == null || currentData.isEmpty()) {
            task.setData(data);
        } else {
            task.setData(currentData + "," + data);
        }
        task.setCompletedParts(task.getCompletedParts() + 1);
        log.info("Updated data {} for ID {}", data, task.getRequestId());
        taskRepository.save(task);
    }

    @Override
    public void updateTaskStatus(String requestId, String status) {
        Task task = taskRepository.findByRequestId(requestId);
        task.setStatus(status);
        log.info("Updated status {} for ID {}", status, task.getRequestId());
        taskRepository.save(task);
    }

    @Override
    public Integer getCountOfCompletedWorkers(String requestId) {
        Task task = taskRepository.findByRequestId(requestId);
        return task != null ? task.getCompletedParts() : 0;
    }
}
