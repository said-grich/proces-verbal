package com.procesverbal.procesverbal.repository;

import com.procesverbal.procesverbal.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepo extends JpaRepository<Document,Long> {

}
