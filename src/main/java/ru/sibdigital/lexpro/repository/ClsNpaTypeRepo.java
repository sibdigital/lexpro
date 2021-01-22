package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsNpaType;

import java.util.List;

@Repository
public interface ClsNpaTypeRepo extends JpaRepository<ClsNpaType, Long> {

    List<ClsNpaType> findAllByOrderByIdAsc();

}
