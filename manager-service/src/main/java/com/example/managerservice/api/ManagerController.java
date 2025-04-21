package com.example.managerservice.api;

import com.example.common.api.Paths;
import com.example.managerservice.core.model.request.HashCrackRequest;
import com.example.managerservice.core.model.request.HashStatusRequest;
import com.example.managerservice.core.model.response.HashCrackResponse;
import com.example.managerservice.core.model.response.HashStatusResponse;
import com.example.managerservice.core.service.impl.ManagerServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(Paths.INTERNAL_API + Paths.MANAGER_HASH)
public class ManagerController {
    private final ManagerServiceImpl service;

    @PostMapping(Paths.CRACK)
    public ResponseEntity<HashCrackResponse> crackHash(@RequestBody HashCrackRequest request) {
        HashCrackResponse response = service.createTask(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(Paths.STATUS)
    public ResponseEntity<HashStatusResponse> hashStatus(@RequestBody HashStatusRequest request) {
        HashStatusResponse response = service.getHashStatus(request);
        return ResponseEntity.ok(response);
    }
}
