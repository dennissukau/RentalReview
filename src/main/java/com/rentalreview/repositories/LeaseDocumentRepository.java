package com.rentalreview.repositories;
import com.rentalreview.entities.LeaseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaseDocumentRepository extends JpaRepository<LeaseDocument, Long> {
}
