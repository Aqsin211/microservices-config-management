package az.company.msemail.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static az.company.msemail.exception.ValidationMessages.CONTENT_CANNOT_BE_BLANK;
import static az.company.msemail.exception.ValidationMessages.RECIPIENT_CANNOT_BE_BLANK;
import static az.company.msemail.exception.ValidationMessages.SUBJECT_CANNOT_BE_BLANK;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class EmailRequestDTO {

    @NotBlank(message = RECIPIENT_CANNOT_BE_BLANK)
    @Email
    String recipient;

    @NotBlank(message = SUBJECT_CANNOT_BE_BLANK)
    String subject;

    @NotBlank(message = CONTENT_CANNOT_BE_BLANK)
    String content;
}
