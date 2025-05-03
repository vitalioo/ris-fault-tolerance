package com.example.managerservice.core;

import com.example.common.model.Status;
import com.example.managerservice.core.entity.Task;
import com.example.managerservice.core.repository.TaskRepository;
import com.example.managerservice.core.service.TaskSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskRetryScheduler {

    private final TaskRepository taskRepository;
    private final TaskSenderService taskService;

    @Scheduled(fixedRate = 15000)
    public void retryNotSentTasks() {
        List<Task> tasks = taskRepository.findAllByStatus(Status.NOT_SENT);
        for (Task t : tasks) {
            taskService.sendTask(t);
        }
    }

    @Scheduled(fixedRate = 30000)
    public void checkTimeouts() {
        List<Task> tasks = taskRepository.findAllByStatus(Status.IN_PROGRESS);
        LocalDateTime now = LocalDateTime.now();
        for (Task t : tasks) {
            if (t.getCreatedAt().plusMinutes(3).isBefore(now)) {
                t.setStatus(Status.ERROR);
                taskRepository.save(t);
            }
        }
    }
}