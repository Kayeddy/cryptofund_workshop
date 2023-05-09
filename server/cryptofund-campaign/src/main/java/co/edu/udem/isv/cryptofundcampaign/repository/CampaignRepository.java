package co.edu.udem.isv.cryptofundcampaign.repository;

import co.edu.udem.isv.cryptofundcampaign.model.Campaign;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findCampaignByUserId(Long userId);

    @Transactional
    @Modifying
    @Query("update Campaign c set c.status = :newStatus where c.campaignId = :campaignId")
    void updateCampaignStatus(@Param("newStatus") Boolean newStatus, @Param("campaignId") Long campaignId);

    @Transactional
    @Modifying
    @Query("update Campaign c set c.description = :newDescription, c.goal = :newGoal, c.deadline = :newDeadline where c.campaignId = :campaignId")
    void updateCampaignDetails(@Param("newDescription") String newDescription,
                               @Param("newGoal") Double newGoal,
                               @Param("newDeadline") Date newDeadline,
                               @Param("campaignId") Long campaignId
    );
}
