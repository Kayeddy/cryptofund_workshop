package co.edu.udem.isv.cryptofundcampaign.cryptofundcampaign.repository;

import co.edu.udem.isv.cryptofundcampaign.model.Campaign;
import co.edu.udem.isv.cryptofundcampaign.repository.CampaignRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CampaignRepositoryTest {
    @Autowired
    private CampaignRepository campaignRepository;

    @Test
    public void CampaignRepository_PostCampaign_ReturnsSavedCampaign() {
        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        Assertions.assertNotNull(savedCampaign);
        Assertions.assertTrue(savedCampaign.getCampaignId() > 0);
    }

    @Test
    public void CampaignRepository_GetCampaign_ReturnsCampaignById() {
        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        Optional<Campaign> foundCampaign = campaignRepository.findById(savedCampaign.getCampaignId());

        Assertions.assertTrue(foundCampaign.isPresent());
        Assertions.assertSame(savedCampaign.getCampaignId(), foundCampaign.get().getCampaignId());
    }

    @Test
    public void CampaignRepository_GetCampaign_ReturnsCampaignByUserId() {
        Long userId = 1L;

        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(userId)
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        Optional<Campaign> foundCampaign = campaignRepository.findCampaignByUserId(userId);

        Assertions.assertTrue(foundCampaign.isPresent());
        Assertions.assertSame(savedCampaign.getUserId(), foundCampaign.get().getUserId());
    }

    @Test
    public void CampaignRepository_UpdateCampaign_ChecksUpdatedStatus() {
        Boolean newStatus = true;

        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        campaignRepository.updateCampaignStatus(newStatus, savedCampaign.getCampaignId());

        Optional<Campaign> foundCampaign = campaignRepository.findById(savedCampaign.getCampaignId());

        Assertions.assertTrue(foundCampaign.isPresent());
        Assertions.assertSame(newStatus, foundCampaign.get().getStatus());
    }

    @Test
    public void CampaignRepository_UpdateCampaign_ChecksUpdatedDetails() {
        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        Campaign newCampaignDetails = Campaign.builder()
                .description("Propuesta de Campaña #1.1")
                .goal(200.0)
                .deadline(LocalDate.parse("2023-12-01"))
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        campaignRepository.updateCampaignDetails(
                newCampaignDetails.getDescription(),
                newCampaignDetails.getGoal(),
                newCampaignDetails.getDeadline(),
                savedCampaign.getCampaignId()
        );

        Optional<Campaign> foundCampaign = campaignRepository.findById(savedCampaign.getCampaignId());

        Assertions.assertTrue(foundCampaign.isPresent());
        Assertions.assertEquals(newCampaignDetails.getDescription(), foundCampaign.get().getDescription());
        Assertions.assertEquals(newCampaignDetails.getGoal(), foundCampaign.get().getGoal());
        Assertions.assertEquals(newCampaignDetails.getDeadline(), foundCampaign.get().getDeadline());
        Assertions.assertSame(savedCampaign.getCampaignId(), foundCampaign.get().getCampaignId());
    }

    @Test
    public void CampaignRepository_DeleteCampaign_ChecksNonExistence() {
        Campaign campaign = Campaign.builder()
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        Campaign savedCampaign = campaignRepository.save(campaign);

        campaignRepository.deleteById(savedCampaign.getCampaignId());

        Optional<Campaign> foundCampaign = campaignRepository.findById(savedCampaign.getCampaignId());

        Assertions.assertTrue(foundCampaign.isEmpty());
    }
}
