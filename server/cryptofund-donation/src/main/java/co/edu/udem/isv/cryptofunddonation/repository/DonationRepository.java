package co.edu.udem.isv.cryptofunddonation.repository;

import co.edu.udem.isv.cryptofunddonation.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByUserId(Long userId);

    List<Donation> findAllByCampaignId(Long campaignId);
}
