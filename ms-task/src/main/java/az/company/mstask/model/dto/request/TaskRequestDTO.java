package az.company.mstask.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static az.company.mstask.exception.ValidationMessages.DESCRIPTION_CANNOT_BE_NULL;
import static az.company.mstask.exception.ValidationMessages.TITLE_CANNOT_BE_NULL;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class TaskRequestDTO {
    @NotNull(message = TITLE_CANNOT_BE_NULL)
    String title;
    @NotNull(message = DESCRIPTION_CANNOT_BE_NULL)
    String description;
}
