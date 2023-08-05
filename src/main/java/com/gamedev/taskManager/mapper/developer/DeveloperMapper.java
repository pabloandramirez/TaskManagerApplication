package com.gamedev.taskManager.mapper.developer;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.model.DeveloperDTO;
import org.springframework.stereotype.Component;

@Component
public interface DeveloperMapper {

    Developer developerDTOtoDeveloper(DeveloperDTO developerDTO);

    DeveloperDTO developerToDeveloperDTO(Developer developer);
}
