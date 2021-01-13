package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsUser;
import ru.sibdigital.lexpro.model.RegUserRole;

import java.util.List;

@Repository
public interface RegUserRoleRepo extends JpaRepository<RegUserRole, Long> {
    List<RegUserRole> findByUser(ClsUser user);
}
