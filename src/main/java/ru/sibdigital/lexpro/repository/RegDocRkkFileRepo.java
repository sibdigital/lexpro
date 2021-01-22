package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegDocRkkFile;

import java.util.List;

@Repository
public interface RegDocRkkFileRepo extends JpaRepository<RegDocRkkFile, Long> {

    List<RegDocRkkFile> findRegDocRkkFileByDocRkkAndHash(DocRkk docRkk, String hash);

    List<RegDocRkkFile> findRegDocRkkFileByDocRkk(DocRkk docRkk);
}
