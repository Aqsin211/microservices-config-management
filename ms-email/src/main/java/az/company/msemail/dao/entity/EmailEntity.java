package az.company.msemail.dao.entity;

import az.company.msemail.model.enums.EmailStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emails")
@FieldDefaults(level = PRIVATE)
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    Long emailId;

    String recipient;
    String subject;
    String content;

    @Column(name = "send_at")
    LocalDateTime sendAt;

    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    @Column(name = "attempt_count")
    private Integer attemptCount;
}
