package com.my.dummy.project.infrastructure.asynccommunication.rabbitmq.consumers.business.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RegisterForReflection
public class TodoMessageQueue {
    private String title;
}
