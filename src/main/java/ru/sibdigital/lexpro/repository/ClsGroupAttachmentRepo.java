package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsGroupAttachment;

import java.util.List;

@Repository
public interface ClsGroupAttachmentRepo extends JpaRepository<ClsGroupAttachment, Long> {
    List<ClsGroupAttachment> findAllByOrderByIdAsc();
}
