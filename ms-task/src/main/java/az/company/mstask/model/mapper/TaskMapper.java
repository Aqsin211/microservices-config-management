package az.company.mstask.model.mapper;

import az.company.mstask.dao.entity.TaskEntity;
import az.company.mstask.model.dto.request.TaskRequestDTO;
import az.company.mstask.model.dto.response.TaskResponseDTO;

import java.time.LocalDateTime;

public enum TaskMapper {
    TASK_MAPPER;
    public TaskEntity mapRequestToEntity(TaskRequestDTO taskRequestDTO) {
        return TaskEntity.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public TaskResponseDTO mapEntityToResponse(TaskEntity taskEntity) {
        return TaskResponseDTO.builder()
                .taskId(taskEntity.getTaskId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .createdAt(taskEntity.getCreatedAt())
                .build();
    }
}
