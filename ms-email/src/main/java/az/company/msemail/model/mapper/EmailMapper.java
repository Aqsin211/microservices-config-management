package az.company.msemail.model.mapper;

import az.company.msemail.dao.entity.EmailEntity;
import az.company.msemail.model.dto.request.EmailRequestDTO;
import az.company.msemail.model.dto.response.EmailResponseDTO;

import java.time.LocalDateTime;

public enum EmailMapper {
    EMAIL_MAPPER;

    public EmailEntity mapRequestToEntity(EmailRequestDTO emailRequestDTO) {
        return EmailEntity.builder()
                .content(emailRequestDTO.getContent())
                .recipient(emailRequestDTO.getRecipient())
                .subject(emailRequestDTO.getSubject())
                .sendAt(LocalDateTime.now())
                .build();
    }

    public EmailResponseDTO mapEntityToResponse(EmailEntity emailEntity) {
        return EmailResponseDTO.builder()
                .emailId(emailEntity.getEmailId())
                .subject(emailEntity.getSubject())
                .recipient(emailEntity.getRecipient())
                .content(emailEntity.getContent())
                .attemptCount(emailEntity.getAttemptCount())
                .status(emailEntity.getStatus())
                .sendAt(emailEntity.getSendAt())
                .build();
    }
}
