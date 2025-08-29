package az.company.mstask.service.concrete;

import az.company.mstask.dao.entity.TaskEntity;
import az.company.mstask.dao.repository.TaskRepository;
import az.company.mstask.exception.LimitReachedException;
import az.company.mstask.exception.NotFoundException;
import az.company.mstask.model.dto.request.TaskRequestDTO;
import az.company.mstask.model.dto.response.IdResponseDTO;
import az.company.mstask.model.dto.response.TaskResponseDTO;
import az.company.mstask.service.abstraction.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.company.mstask.model.enums.SystemMessages.MAX_TASK_LIMIT_REACHED;
import static az.company.mstask.model.enums.SystemMessages.NOT_FOUND;
import static az.company.mstask.model.mapper.TaskMapper.TASK_MAPPER;
import static java.lang.String.format;

@RefreshScope
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Value("${max.task}")
    private Long taskCapacity;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public IdResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        long tasksLeft = taskCapacity - taskRepository.count();
        if (tasksLeft <= 0) {
            throw new LimitReachedException(MAX_TASK_LIMIT_REACHED.getMessage());
        }

        TaskEntity savedTask = taskRepository.save(TASK_MAPPER.mapRequestToEntity(taskRequestDTO));

        return IdResponseDTO.builder()
                .taskId(savedTask.getTaskId())
                .tasksLeft(tasksLeft - 1)
                .build();
    }

    @Override
    public TaskResponseDTO getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .map(TASK_MAPPER::mapEntityToResponse)
                .orElseThrow(
                        () -> new NotFoundException(format(NOT_FOUND.getMessage(), taskId))
                );
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TASK_MAPPER::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new NotFoundException(format(NOT_FOUND.getMessage(), taskId));
        }
    }

    @Override
    public void updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new NotFoundException(format(NOT_FOUND.getMessage(), taskId))
                );
        if (taskRequestDTO.getDescription() != null && !taskRequestDTO.getDescription().isEmpty()) {
            taskEntity.setDescription(taskRequestDTO.getDescription());
        }
        if (taskRequestDTO.getTitle() != null && !taskRequestDTO.getTitle().isEmpty()) {
            taskEntity.setTitle(taskRequestDTO.getTitle());
        }
        taskRepository.save(taskEntity);
    }
}
