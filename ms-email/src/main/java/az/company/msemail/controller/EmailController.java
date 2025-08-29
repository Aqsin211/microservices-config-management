package az.company.msemail.controller;

import az.company.msemail.model.dto.request.EmailRequestDTO;
import az.company.msemail.model.dto.response.EmailResponseDTO;
import az.company.msemail.model.dto.response.IdResponseDTO;
import az.company.msemail.service.concrete.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static az.company.msemail.model.enums.SystemMessages.DELETED_FROM_DATABASE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final EmailServiceImpl emailServiceImpl;

    public EmailController(EmailServiceImpl emailServiceImpl) {
        this.emailServiceImpl = emailServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public IdResponseDTO sendEmail(@Valid @RequestBody EmailRequestDTO emailRequestDTO) {
        return emailServiceImpl.sendEmail(emailRequestDTO);
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<EmailResponseDTO> getEmail(@PathVariable Long emailId) {
        return ResponseEntity.ok(emailServiceImpl.getEmail(emailId));
    }

    @DeleteMapping("/{emailId}")
    public ResponseEntity<String> deleteEmail(@PathVariable Long emailId) {
        emailServiceImpl.deleteEmail(emailId);
        return ResponseEntity.ok(DELETED_FROM_DATABASE.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<EmailResponseDTO>> getAllEmails() {
        return ResponseEntity.ok(emailServiceImpl.getAll());
    }
}
