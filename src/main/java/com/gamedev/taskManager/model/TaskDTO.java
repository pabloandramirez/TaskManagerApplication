package com.gamedev.taskManager.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private String description;
    private String state;
    private String deadLine;
    private String idDeveloper;
    private String idGame;
}
