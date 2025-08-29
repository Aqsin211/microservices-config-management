package az.company.msemail.service.concrete;

import az.company.msemail.dao.entity.EmailEntity;
import az.company.msemail.dao.repository.EmailRepository;
import az.company.msemail.exception.LimitReachedException;
import az.company.msemail.exception.NotFoundException;
import az.company.msemail.model.dto.request.EmailRequestDTO;
import az.company.msemail.model.dto.response.EmailResponseDTO;
import az.company.msemail.model.dto.response.IdResponseDTO;
import az.company.msemail.service.abstraction.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.company.msemail.model.enums.EmailStatus.FAILED;
import static az.company.msemail.model.enums.EmailStatus.PENDING;
import static az.company.msemail.model.enums.EmailStatus.SENT;
import static az.company.msemail.model.enums.SystemMessages.*;
import static az.company.msemail.model.mapper.EmailMapper.EMAIL_MAPPER;
import static java.lang.String.format;

@RefreshScope
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Value("${send.from}")
    private String senderEmail;

    @Value("${max.retries}")
    private int maxRetries;

    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    @Override
    public IdResponseDTO sendEmail(EmailRequestDTO emailRequestDTO) {
        EmailEntity emailEntity = EMAIL_MAPPER.mapRequestToEntity(emailRequestDTO);
        emailEntity.setStatus(PENDING);
        emailEntity.setAttemptCount(0);
        emailEntity = emailRepository.save(emailEntity);

        int attempt = 0;
        boolean sent = false;

        while (attempt < maxRetries && !sent) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(emailRequestDTO.getRecipient());
                helper.setFrom(senderEmail);
                helper.setSubject(emailRequestDTO.getSubject());
                helper.setText(emailRequestDTO.getContent(), true);

                mailSender.send(message);
                sent = true;

                emailEntity.setStatus(SENT);
                emailEntity.setAttemptCount(attempt + 1);
                emailRepository.save(emailEntity);

            } catch (MessagingException | MailException e) {
                attempt++;
                emailEntity.setAttemptCount(attempt);

                if (attempt >= maxRetries) {
                    emailEntity.setStatus(FAILED);
                    emailRepository.save(emailEntity);
                    throw new LimitReachedException(MAX_RETRIES_REACHED.getMessage());
                } else {
                    emailRepository.save(emailEntity);
                    try {
                        Thread.sleep(1000L * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        return IdResponseDTO.builder()
                .emailId(emailEntity.getEmailId())
                .build();
    }


    @Override
    public EmailResponseDTO getEmail(Long emailId) {
        return emailRepository
                .findById(emailId)
                .map(EMAIL_MAPPER::mapEntityToResponse)
                .orElseThrow(
                        () -> new NotFoundException(format(EMAIL_NOT_FOUND.getMessage(), emailId))
                );
    }

    @Override
    public void deleteEmail(Long emailId) {
        if (emailRepository.existsById(emailId)) {
            emailRepository.deleteById(emailId);
        } else {
            throw new NotFoundException(format(EMAIL_NOT_FOUND.getMessage(), emailId));
        }
    }

    @Override
    public List<EmailResponseDTO> getAll() {
        return emailRepository
                .findAll()
                .stream()
                .map(EMAIL_MAPPER::mapEntityToResponse)
                .collect(Collectors.toList());
    }
}
