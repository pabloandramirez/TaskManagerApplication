package com.gamedev.taskManager.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {
    private String title;
    private String description;
    private String releaseData;
}
