package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsEmployee;

import java.util.List;

@Repository
public interface ClsEmployeeRepo extends JpaRepository<ClsEmployee, Long> {

    List<ClsEmployee> findAllByOrderByIdAsc();
}
