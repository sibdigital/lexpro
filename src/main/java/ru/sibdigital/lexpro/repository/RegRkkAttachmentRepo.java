package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkAttachment;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegRkkAttachmentRepo extends JpaRepository<RegRkkAttachment, Long> {


    Optional<List<RegRkkAttachment>> findAllByDocRkk(DocRkk docRkk);
    Optional<List<RegRkkAttachment>> findAllByDocRkkAndIsDeleted(DocRkk docRkk, Boolean isDeleted);
}
