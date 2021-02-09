package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkVisa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RegRkkVisaRepo extends JpaRepository<RegRkkVisa, Long> {

    Optional<List<RegRkkVisa>> findAllByDocRkk(DocRkk docRkk);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reg_rkk_visa\n" +
            "WHERE id in (:id_visas)",
            nativeQuery = true)
    void deleteBySetId(@Param("id_visas") Set<Long> idList);
}
