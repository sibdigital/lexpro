package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsTypeAttachment;

import java.util.List;

@Repository
public interface ClsTypeAttachmentRepo extends JpaRepository<ClsTypeAttachment, Long> {
    List<ClsTypeAttachment> findAllByOrderByIdAsc();
}
