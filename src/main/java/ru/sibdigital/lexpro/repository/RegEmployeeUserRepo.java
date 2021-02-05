package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsUser;
import ru.sibdigital.lexpro.model.RegEmployeeUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegEmployeeUserRepo extends JpaRepository<RegEmployeeUser, Long> {
    Optional<RegEmployeeUser> findFirstByUser_Id(Long userId);
}
