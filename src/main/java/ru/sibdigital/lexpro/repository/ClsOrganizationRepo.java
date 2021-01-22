package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.ClsOrganization;

import java.util.List;

@Repository
public interface ClsOrganizationRepo extends JpaRepository<ClsOrganization, Long> {

    List<ClsOrganization> findAllByOrderByIdAsc();
}
