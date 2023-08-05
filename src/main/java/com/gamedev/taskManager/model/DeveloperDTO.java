package com.gamedev.taskManager.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDTO {
    private String name;
    private String email;
    private String role;
}
