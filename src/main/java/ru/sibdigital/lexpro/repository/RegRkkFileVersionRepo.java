package ru.sibdigital.lexpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sibdigital.lexpro.model.RegRkkFileVersion;

@Repository
public interface RegRkkFileVersionRepo extends JpaRepository<RegRkkFileVersion, Long> {

}
