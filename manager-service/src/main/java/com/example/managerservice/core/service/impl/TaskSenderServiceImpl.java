package com.example.managerservice.core.service.impl;

import com.example.common.model.Status;
import com.example.managerservice.core.entity.Task;
import com.example.managerservice.core.model.xml.CrackHashManagerRequest;
import com.example.managerservice.core.model.CrackHashMapper;
import com.example.managerservice.core.repository.TaskRepository;
import com.example.managerservice.core.service.TaskSenderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskSenderServiceImpl implements TaskSenderService {

    @Value("${worker.count}")
    private Integer workerCount;

    @Value("${rabbitmq.tasks.exchange}")
    private String taskExchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    private final TaskRepository taskRepository;

    @Override
    public void sendTask(Task task) {
        for (int i = 0; i < workerCount; i++) {
            CrackHashManagerRequest req = CrackHashMapper.mapToXmlRequest(task, i, workerCount);
            try {
                log.info("Send task for worker {}", i);
                rabbitTemplate.convertAndSend(taskExchange, routingKey, req, msg -> {
                    msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return msg;
                });
            } catch (AmqpException e) {
                task.setStatus(Status.NOT_SENT);
                log.error("Catch exception {} for task id {}: ", e.getLocalizedMessage(), task.getRequestId());
                taskRepository.save(task);
                return;
            }
        }
        log.info("Task with ID {} was send", task.getRequestId());
        task.setStatus(Status.IN_PROGRESS);
        taskRepository.save(task);
    }

}
