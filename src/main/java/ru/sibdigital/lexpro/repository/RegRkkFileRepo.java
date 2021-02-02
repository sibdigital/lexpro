package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;

import java.util.List;

@Repository
public interface RegRkkFileRepo extends JpaRepository<RegRkkFile, Long> {

    List<RegRkkFile> findRegRkkFileByDocRkkAndHash(DocRkk docRkk, String hash);

    List<RegRkkFile> findRegRkkFileByDocRkk(DocRkk docRkk);
}
