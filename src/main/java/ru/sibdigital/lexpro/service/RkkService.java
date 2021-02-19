package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.model.*;

public interface RkkService {

    Page<DocRkk> findActiveDocRkks(int page, int size);

    Page<DocRkk> findDeletedDocRkks(int page, int size);

    Page<DocRkk> findArchivedDocRkks(int page, int size);

    DocRkk saveDocRkk(DocRkkDto docRkkDto);

    DocRkk archiveDocRkk(DocRkkDto docRkkDto);

    DocRkk deleteDocRkk(DocRkkDto docRkkDto);

    DocRkk restoreDocRkk(DocRkkDto docRkkDto);

    DocRkk rearchiveDocRkk(DocRkkDto docRkkDto);

}
