package com.rentalreview.repositories;
import com.rentalreview.entities.OwnershipClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnershipClaimRepository extends JpaRepository<OwnershipClaim, Long> {
}
