package co.edu.udem.isv.cryptofundcampaign.cryptofundcampaign.service;

import co.edu.udem.isv.cryptofundcampaign.model.Campaign;
import co.edu.udem.isv.cryptofundcampaign.repository.CampaignRepository;
import co.edu.udem.isv.cryptofundcampaign.service.CampaignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceTest {
    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    public void CampaignService_PostCampaign_ReturnsSavedCampaign() {
        Campaign campaign = Campaign.builder()
                .campaignId(1L)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        when(campaignRepository.save(Mockito.any(Campaign.class))).thenReturn(campaign);

        Campaign savedCampaign = campaignService.saveCampaign(campaign);

        Assertions.assertNotNull(savedCampaign);
        Assertions.assertTrue(savedCampaign.getUserId() > 0);
    }

    @Test
    public void CampaignService_GetCampaign_ReturnsCampaignByID() {
        Long campaignId = 1L;

        Campaign campaign = Campaign.builder()
                .campaignId(campaignId)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        when(campaignRepository.findById(campaignId)).thenReturn(Optional.ofNullable(campaign));

        Optional<Campaign> foundCampaign = campaignService.findCampaignById(campaignId);

        Assertions.assertTrue(foundCampaign.isPresent());
        assert campaign != null;
        Assertions.assertSame(campaign.getCampaignId(), foundCampaign.get().getCampaignId());
    }

    @Test
    public void CampaignService_GetCampaign_ReturnsCampaignByUserId() {
        Long userId = 1L;

        Campaign campaign = Campaign.builder()
                .campaignId(1L)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(userId)
                .build();

        when(campaignRepository.findCampaignByUserId(userId)).thenReturn(Optional.ofNullable(campaign));

        Optional<Campaign> foundCampaign = campaignService.findCampaignByUserId(userId);

        Assertions.assertTrue(foundCampaign.isPresent());
        assert campaign != null;
        Assertions.assertSame(campaign.getUserId(), foundCampaign.get().getUserId());
    }

    @Test
    public void CampaignService_UpdateCampaign_ChecksUpdatedStatus() {
        Long campaignId = 1L;
        Boolean newStatus = true;

        doNothing().when(campaignRepository).updateCampaignStatus(newStatus, campaignId);

        campaignService.updateCampaignStatus(newStatus, campaignId);

        verify(campaignRepository, times(1)).updateCampaignStatus(newStatus, campaignId);
    }

    @Test
    public void CampaignService_UpdateCampaign_ChecksUpdatedDetails() {
        Long campaignId = 1L;

        Campaign newCampaignDetails = Campaign.builder()
                .description("Propuesta de Campaña #1.1")
                .goal(200.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .build();

        doNothing().when(campaignRepository).updateCampaignDetails(
                newCampaignDetails.getDescription(),
                newCampaignDetails.getGoal(),
                newCampaignDetails.getDeadline(),
                campaignId
        );

        campaignService.updateCampaignDetails(campaignId, newCampaignDetails);

        verify(campaignRepository, times(1)).updateCampaignDetails(
                newCampaignDetails.getDescription(),
                newCampaignDetails.getGoal(),
                newCampaignDetails.getDeadline(),
                campaignId
        );
    }

    @Test
    public void CampaignService_DeleteCampaign_ChecksNonExistence() {
        Long campaignId = 1L;

        doNothing().when(campaignRepository).deleteById(campaignId);

        campaignService.deleteCampaign(campaignId);

        verify(campaignRepository, times(1)).deleteById(campaignId);
    }
}

