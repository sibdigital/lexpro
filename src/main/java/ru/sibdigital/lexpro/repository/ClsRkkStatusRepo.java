package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsRkkStatus;

import java.util.List;

@Repository
public interface ClsRkkStatusRepo extends JpaRepository<ClsRkkStatus, Long> {

    List<ClsRkkStatus> findAllByOrderByIdAsc();

}
