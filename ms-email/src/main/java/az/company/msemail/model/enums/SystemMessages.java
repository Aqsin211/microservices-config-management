package az.company.msemail.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemMessages {
    FAILED_TO_SEND_EMAIL("Failed to send email"),
    EMAIL_NOT_FOUND("Email not found with id: %s"),
    DELETED_FROM_DATABASE("DELETED FROM DATABASE"),
    MAX_RETRIES_REACHED("Max retries reached");
    private final String message;
}
