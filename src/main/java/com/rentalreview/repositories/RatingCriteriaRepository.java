package com.rentalreview.repositories;

import com.rentalreview.entities.RatingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingCriteriaRepository extends JpaRepository<RatingCriteria, Long> {
}
