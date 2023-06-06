package co.edu.udem.isv.cryptofundcampaign.controller;

import co.edu.udem.isv.cryptofundcampaign.model.Campaign;
import co.edu.udem.isv.cryptofundcampaign.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/post")
    public Campaign postCampaign(@RequestBody Campaign campaign) {
        return this.campaignService.saveCampaign(campaign);
    }

    @GetMapping("/get/{id}")
    public Optional<Campaign> getCampaignById(@PathVariable Long id) {
        return this.campaignService.findCampaignById(id);
    }

    @GetMapping("/get/user/{id}")
    public Optional<Campaign> getCampaignByUserId(@PathVariable Long id) {
        return this.campaignService.findCampaignByUserId(id);
    }

    @GetMapping("/get")
    public List<Campaign> getAllCampaigns() {
        return this.campaignService.findAllCampaigns();
    }

    @PutMapping(value = "/put/{id}", params = "status")
    public void putCampaignStatus(@RequestParam Boolean status, @PathVariable Long id) {
        campaignService.updateCampaignStatus(status, id);
    }

    @PutMapping(value = "/put/{id}")
    public void putCampaignStatus(@PathVariable Long id, @RequestBody Campaign campaign) {
        campaignService.updateCampaignDetails(id, campaign);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
    }
}
