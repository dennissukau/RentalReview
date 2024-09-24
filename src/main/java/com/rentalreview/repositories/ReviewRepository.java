package com.rentalreview.repositories;

import com.rentalreview.entities.Property;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProperty(Property property);
    List<Review> findByUser(User user);
}
 