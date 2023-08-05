package com.gamedev.taskManager.services.developer;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.TaskDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

public interface DeveloperService {

    Optional<DeveloperDTO> getDeveloperById(UUID idDeveloper);

    Developer createDeveloper(@RequestBody DeveloperDTO developerDTO);

    boolean assignTask(UUID developerId, UUID gameId, @RequestBody TaskDTO taskDTO) throws NotFoundException;
}
