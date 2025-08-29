package az.company.msemail.service.abstraction;

import az.company.msemail.model.dto.request.EmailRequestDTO;
import az.company.msemail.model.dto.response.EmailResponseDTO;
import az.company.msemail.model.dto.response.IdResponseDTO;

import java.util.List;

public interface EmailService {
    IdResponseDTO sendEmail(EmailRequestDTO emailRequestDTO);

    EmailResponseDTO getEmail(Long emailId);

    void deleteEmail(Long emailId);

    List<EmailResponseDTO> getAll();
}
