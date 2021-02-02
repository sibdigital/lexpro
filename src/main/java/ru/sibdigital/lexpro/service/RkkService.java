package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.model.*;

import java.util.List;

public interface RkkService {

    Page<DocRkk> findAllDocRkk(int page, int size);

    List<ClsRkkStatus> getRkkStatusList();

    List<ClsNpaType> getNpaTypeList();

    List<ClsOrganization> getOrganizationList();

    List<ClsEmployee> getEmployeeList();

    List<ClsSession> getSessionList();

    DocRkk saveDocRkk(DocRkkDto docRkkDto);

}
