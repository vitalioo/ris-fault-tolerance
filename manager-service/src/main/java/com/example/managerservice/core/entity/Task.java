package com.example.managerservice.core.entity;

import com.example.common.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Task {
    @Id
    private String requestId;

    private String hash;

    private Integer maxLength;

    private LocalDateTime createdAt;

    @Builder.Default
    private String status = Status.IN_PROGRESS;

    private String data;

    @Builder.Default
    private Integer completedParts = 0;
}
