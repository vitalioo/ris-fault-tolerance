package com.example.workerservice.service.impl;

import com.example.workerservice.model.CrackHashManagerRequest;
import com.example.workerservice.model.exception.ConsumeTaskException;
import com.example.workerservice.model.response.CrackHashWorkerResponse;
import com.example.workerservice.service.BruteForceService;
import com.example.workerservice.service.WorkerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final BruteForceService bruteForceService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.result.exchange}")
    private String resultExchange;
    @Value("${rabbitmq.result.key}")
    private String resultRoutingKey;

    @RabbitListener(queues = "${rabbitmq.task.queue}")
    public void consumeTask(CrackHashManagerRequest request) {
        try {
            log.info("Get task: {}", request.getRequestId());

            List<String> found = bruteForceService.processTask(
                    request.getHash(),
                    request.getMaxLength(),
                    request.getAlphabet(),
                    request.getPartNumber(),
                    request.getPartCount()
            );

            String joined = String.join(",", found);
            log.info("Result: {} for requestId: {}", joined, request.getRequestId());

            CrackHashWorkerResponse response = new CrackHashWorkerResponse(request.getRequestId(), joined);
            rabbitTemplate.convertAndSend(resultExchange, resultRoutingKey, response);

        } catch (Exception e) {
            log.error("Failed consume task: {}", request.getRequestId(), e);
            throw new ConsumeTaskException(e.getLocalizedMessage());
        }
    }

}
