package co.edu.udem.isv.cryptofunddonation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "donations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    private Long userId;

    private Long campaignId;

    private Double amount;

    private LocalDate donationDate = LocalDate.now();
}
