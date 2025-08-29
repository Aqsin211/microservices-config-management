package az.company.mstask.controller;

import az.company.mstask.model.dto.request.TaskRequestDTO;
import az.company.mstask.model.dto.response.IdResponseDTO;
import az.company.mstask.model.dto.response.TaskResponseDTO;
import az.company.mstask.service.concrete.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static az.company.mstask.model.enums.SystemMessages.DELETED;
import static az.company.mstask.model.enums.SystemMessages.UPDATED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public IdResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return taskServiceImpl.createTask(taskRequestDTO);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskServiceImpl.getTask(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> delete(@PathVariable Long taskId) {
        taskServiceImpl.deleteTask(taskId);
        return ResponseEntity.ok(DELETED.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAll() {
        return ResponseEntity.ok(taskServiceImpl.getAllTasks());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        taskServiceImpl.updateTask(taskId, taskRequestDTO);
        return ResponseEntity.ok(UPDATED.getMessage());
    }

}
