package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.DocRkk;

@Repository
public interface DocRkkRepo extends JpaRepository<DocRkk, Long> {
}
