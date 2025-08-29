package az.company.mstask.service.abstraction;

import az.company.mstask.model.dto.request.TaskRequestDTO;
import az.company.mstask.model.dto.response.IdResponseDTO;
import az.company.mstask.model.dto.response.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    IdResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO getTask(Long taskId);

    List<TaskResponseDTO> getAllTasks();

    void deleteTask(Long taskId);

    void updateTask(Long taskId, TaskRequestDTO taskRequestDTO);
}
