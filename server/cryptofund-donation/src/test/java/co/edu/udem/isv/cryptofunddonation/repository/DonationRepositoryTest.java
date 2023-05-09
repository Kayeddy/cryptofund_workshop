package co.edu.udem.isv.cryptofunddonation.repository;

import co.edu.udem.isv.cryptofunddonation.model.Donation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DonationRepositoryTest {
    @Autowired
    private DonationRepository donationRepository;

    @Test
    public void DonationRepository_PostDonation_ReturnsSavedDonation() {
        Donation donation = Donation.builder()
                .userId(1L)
                .campaignId(1L)
                .amount(10.0)
                .build();

        Donation savedDonation = donationRepository.save(donation);

        Assertions.assertNotNull(savedDonation);
        Assertions.assertTrue(savedDonation.getUserId() > 0);
    }

    @Test
    public void DonationRepository_GetDonation_ReturnsDonationById() {
        Donation donation = Donation.builder()
                .userId(1L)
                .campaignId(1L)
                .amount(10.0)
                .build();

        Donation savedDonation = donationRepository.save(donation);

        Optional<Donation> foundDonation = donationRepository.findById(savedDonation.getDonationId());

        Assertions.assertTrue(foundDonation.isPresent());
        Assertions.assertSame(savedDonation.getDonationId(), foundDonation.get().getDonationId());
    }

    @Test
    public void DonationRepository_GetDonations_ReturnsDonationsByUserId() {
        Long userId = 1L;

        Donation donation1 = Donation.builder()
                .userId(userId)
                .campaignId(1L)
                .amount(10.0)
                .build();

        Donation donation2 = Donation.builder()
                .userId(userId)
                .campaignId(2L)
                .amount(10.0)
                .build();

        donationRepository.save(donation1);
        donationRepository.save(donation2);

        List<Donation> foundDonations = donationRepository.findAllByUserId(userId);

        Assertions.assertFalse(foundDonations.isEmpty());
        Assertions.assertTrue(foundDonations.stream().allMatch(d -> d.getUserId().equals(userId)));
    }

    @Test
    public void DonationRepository_GetDonations_ReturnsDonationsByCampaignId() {
        Long campaignId = 1L;

        Donation donation1 = Donation.builder()
                .userId(1L)
                .campaignId(campaignId)
                .amount(10.0)
                .build();

        Donation donation2 = Donation.builder()
                .userId(2L)
                .campaignId(campaignId)
                .amount(10.0)
                .build();

        donationRepository.save(donation1);
        donationRepository.save(donation2);

        List<Donation> foundDonations = donationRepository.findAllByCampaignId(campaignId);

        Assertions.assertFalse(foundDonations.isEmpty());
        Assertions.assertTrue(foundDonations.stream().allMatch(d -> d.getCampaignId().equals(campaignId)));
    }
}
