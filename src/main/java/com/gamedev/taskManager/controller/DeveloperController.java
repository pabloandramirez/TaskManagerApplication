package com.gamedev.taskManager.controller;

import com.gamedev.taskManager.bootstrap.constants.Role;
import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.exceptions.BadRequestException;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.TaskDTO;
import com.gamedev.taskManager.repository.developer.DeveloperRepository;
import com.gamedev.taskManager.services.developer.DeveloperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/developer")
@RequiredArgsConstructor
@Slf4j
public class DeveloperController {

    private final DeveloperService developerService;
    private final DeveloperRepository developerRepository;

    @GetMapping("/{idDeveloper}")
    public DeveloperDTO getDeveloperById(@PathVariable(value = "idDeveloper") UUID idDeveloper)
            throws NotFoundException {
        return developerService.getDeveloperById(idDeveloper).orElseThrow(NotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<Void> createDeveloper(@RequestBody DeveloperDTO developerDTO)
            throws BadRequestException {
        if(!developerDTO.getRole().equalsIgnoreCase(Role.QA.getRole())&&
                !developerDTO.getRole().equalsIgnoreCase(Role.DEV.getRole())&&
                !developerDTO.getRole().equalsIgnoreCase(Role.DEV_OPS.getRole())){
            throw new BadRequestException("There are no roles with this name");
        } else{
            log.info("Creacion de un desarrollador para el equipo");
            Developer developerCreated = developerService.createDeveloper(developerDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/developer/"+developerCreated.getUuid());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{developerId}/assignTask/{gameId}")
    public ResponseEntity<Void> assignTask(@PathVariable(value = "developerId") UUID developerId,
                                           @PathVariable(value = "gameId") UUID gameId,
                                           @RequestBody TaskDTO taskDTO) throws BadRequestException, NotFoundException {
        if(developerService.assignTask(developerId, gameId, taskDTO)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/developer/"
                    +developerRepository.findById(developerId).get().getUuid()+"/task");
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            throw new BadRequestException();
        }
    }


}
