package co.edu.udem.isv.cryptofunddonation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "donations")
@Data
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    private Long userId;

    private Long campaignId;

    private Double amount;

    private LocalDate donationDate = LocalDate.now();

    public Donation() {
    }

    public Donation(Long donationId, Long userId, Long campaignId, Double amount) {
        this.donationId = donationId;
        this.userId = userId;
        this.campaignId = campaignId;
        this.amount = amount;
    }
}
