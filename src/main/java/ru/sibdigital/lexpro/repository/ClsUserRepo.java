package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsUser;

@Repository
public interface ClsUserRepo extends JpaRepository<ClsUser, Long> {

    ClsUser findByLogin(String login);

}
