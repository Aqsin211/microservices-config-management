package az.company.mstask.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemMessages {
    NOT_FOUND("No task found with id: %s"),
    MAX_TASK_LIMIT_REACHED("Max task limit reached"),
    UPDATED("UPDATED"),
    DELETED("DELETED");
    private final String message;
}
