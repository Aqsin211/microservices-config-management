package az.company.msemail.model.dto.response;

import az.company.msemail.model.enums.EmailStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailResponseDTO {
    Long emailId;
    String recipient;
    String subject;
    String content;
    EmailStatus status;
    Integer attemptCount;
    LocalDateTime sendAt;
}
