package co.edu.udem.isv.cryptofundcampaign.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "campaigns")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campaignId;

    private String title;

    private String description;

    private Double goal;

    private LocalDate deadline;

    private Boolean status = false;

    private Long userId;

}
