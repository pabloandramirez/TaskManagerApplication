package com.gamedev.taskManager.mapper.developer.impl;

import com.gamedev.taskManager.bootstrap.constants.Role;
import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.mapper.developer.DeveloperMapper;
import com.gamedev.taskManager.model.DeveloperDTO;
import org.aspectj.weaver.Position;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DeveloperMapperImpl implements DeveloperMapper {
    @Override
    public Developer developerDTOtoDeveloper(DeveloperDTO developerDTO) {
        return Developer.builder()
                .uuid(UUID.randomUUID())
                .name(developerDTO.getName())
                .email(developerDTO.getEmail())
                .role(getRole(developerDTO.getRole()))
                .build();
    }

    @Override
    public DeveloperDTO developerToDeveloperDTO(Developer developer) {
        return DeveloperDTO.builder()
                .name(developer.getName())
                .email(developer.getEmail())
                .role(getRole(developer.getRole()))
                .build();
    }

    private Role getRole(String roleString){
        if(!roleString.isBlank()){
            for (Role role: Role.values()) {
                if (role.getRole().equalsIgnoreCase(roleString)) {
                    return role;
                }
            }
        }
        return null;
    }

    private String getRole(Role role){
        return role.getRole();
    }
}
