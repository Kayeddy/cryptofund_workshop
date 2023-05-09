package co.edu.udem.isv.cryptofundcampaign.cryptofundcampaign.controller;

import co.edu.udem.isv.cryptofundcampaign.controller.CampaignController;
import co.edu.udem.isv.cryptofundcampaign.model.Campaign;
import co.edu.udem.isv.cryptofundcampaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CampaignController.class)
public class CampaignControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CampaignService campaignService;

    @Test
    public void CampaignController_PostCampaign_ReturnsSavedCampaign() throws Exception {
        given(campaignService.saveCampaign(any(Campaign.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        Campaign campaign = Campaign.builder()
                .campaignId(1L)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .status(false)
                .userId(1L)
                .build();

        this.mockMvc.perform(post("/api/campaigns/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campaign)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.campaignId", is(campaign.getCampaignId().intValue())));
    }

    @Test
    public void CampaignController_GetCampaign_ReturnsCampaignById() throws Exception {
        Long campaignId = 1L;

        Campaign campaign = Campaign.builder()
                .campaignId(campaignId)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(1L)
                .build();

        given(campaignService.findCampaignById(campaignId)).willReturn(Optional.of(campaign));

        this.mockMvc.perform(get("/api/campaigns/get/{id}", campaignId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(campaign.getCampaignId().intValue())));
    }

    @Test
    public void CampaignController_GetCampaign_ReturnsCampaignByUserId() throws Exception {
        Long userId = 1L;

        Campaign campaign = Campaign.builder()
                .campaignId(1L)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .userId(userId)
                .build();

        given(campaignService.findCampaignByUserId(userId)).willReturn(Optional.of(campaign));

        this.mockMvc.perform(get("/api/campaigns/get/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(campaign.getUserId().intValue())))
                .andExpect(jsonPath("$.campaignId", is(campaign.getCampaignId().intValue())));
    }

    @Test
    public void CampaignController_UpdateCampaign_ChecksUpdatedStatus() throws Exception {
        Long campaignId = 1L;
        Boolean newStatus = true;

        doNothing().when(campaignService).updateCampaignStatus(newStatus, campaignId);

        this.mockMvc.perform(put("/api/campaigns/put/{id}", campaignId).param("status", String.valueOf(newStatus)))
                .andExpect(status().isOk());

        verify(campaignService, times(1)).updateCampaignStatus(newStatus, campaignId);
    }

    @Test
    public void CampaignController_UpdateCampaign_ChecksUpdatedDetails() throws Exception {
        Long campaignId = 1L;

        Campaign newCampaignDetails = Campaign.builder()
                .campaignId(1L)
                .title("Campaña #1")
                .description("Propuesta de Campaña #1")
                .goal(100.0)
                .deadline(LocalDate.parse("2023-06-01"))
                .status(false)
                .userId(1L)
                .build();

        doNothing().when(campaignService).updateCampaignDetails(campaignId, newCampaignDetails);

        this.mockMvc.perform(put("/api/campaigns/put/{id}", campaignId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCampaignDetails)))
                .andExpect(status().isOk());

        verify(campaignService, times(1)).updateCampaignDetails(campaignId, newCampaignDetails);
    }

    @Test
    public void UserController_DeleteUser_ChecksNonExistence() throws Exception {
        Long campaignId = 1L;

        doNothing().when(campaignService).deleteCampaign(campaignId);

        this.mockMvc.perform(delete("/api/campaigns/delete/{id}", campaignId))
                .andExpect(status().isOk());

        verify(campaignService, times(1)).deleteCampaign(campaignId);
    }
}
