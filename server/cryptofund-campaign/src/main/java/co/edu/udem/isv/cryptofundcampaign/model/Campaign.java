package co.edu.udem.isv.cryptofundcampaign.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "campaigns")
@Data
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campaignId;

    private String title;

    private String description;

    private Double goal;

    private Date deadline;

    private Boolean status = false;

    private Long userId;

    public Campaign() {
    }
}
