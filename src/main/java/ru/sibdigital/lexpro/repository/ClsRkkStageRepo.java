package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsRkkStage;

import java.util.List;

@Repository
public interface ClsRkkStageRepo extends JpaRepository<ClsRkkStage, Long> {

    List<ClsRkkStage> findAllByOrderByIdAsc();

}
