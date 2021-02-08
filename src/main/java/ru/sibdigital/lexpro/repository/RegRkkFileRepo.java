package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegRkkFileRepo extends JpaRepository<RegRkkFile, Long> {

    Optional<List<RegRkkFile>> findAllByDocRkkAndIsDeleted(DocRkk docRkk, Boolean isDeleted);
}
