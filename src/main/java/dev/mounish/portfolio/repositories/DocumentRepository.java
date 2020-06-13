package dev.mounish.portfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dev.mounish.portfolio.models.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

	List<Document> findByKeyOrderByCreationDateDesc(final String key);

}
