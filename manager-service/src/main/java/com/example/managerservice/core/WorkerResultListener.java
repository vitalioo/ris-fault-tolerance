package com.example.managerservice.core;

import com.example.common.model.Status;
import com.example.managerservice.core.model.xml.CrackHashWorkerResponse;
import com.example.managerservice.core.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerResultListener {

    private final ManagerService managerService;

    @RabbitListener(queues = "${rabbitmq.result.queue}")
    public void handleWorkerResult(CrackHashWorkerResponse response) {
        String found = response.getFoundWords();
        if (found != null && !found.isBlank()) {
            managerService.updateTaskData(response.getRequestId(), found);
        } else {
            managerService.updateTaskData(response.getRequestId(), "");
        }

        int completed = managerService.getCountOfCompletedWorkers(response.getRequestId());
        if (completed == 2) {
            managerService.updateTaskStatus(response.getRequestId(), Status.READY);
        }
    }
}
