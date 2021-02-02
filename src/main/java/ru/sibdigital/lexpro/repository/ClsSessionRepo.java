package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsSession;

import java.util.List;

@Repository
public interface ClsSessionRepo extends JpaRepository<ClsSession, Long> {

    List<ClsSession> findAllByOrderByIdAsc();
}
