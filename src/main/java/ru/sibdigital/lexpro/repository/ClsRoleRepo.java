package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsRole;

@Repository
public interface ClsRoleRepo extends JpaRepository<ClsRole, Long> {

}
