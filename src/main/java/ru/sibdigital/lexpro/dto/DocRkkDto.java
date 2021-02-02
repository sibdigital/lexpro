package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sibdigital.lexpro.model.ClsEmployee;
import ru.sibdigital.lexpro.model.ClsNpaType;
import ru.sibdigital.lexpro.model.ClsOrganization;
import ru.sibdigital.lexpro.model.ClsRkkStatus;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocRkkDto {

    private Long            id;
    private String          rkkNumber;
    private String          npaName;
    private String          npaType;
    private String          registrationDate;
    private String          introductionDate;
    private String          legislativeBasis;
    private String          lawSubject;
    private String          speaker;
    private Boolean         readyForSession;
    private String          deadline;
    private String          includedInAgenda;
    private String          responsibleOrganization;
    private String          responsibleEmployee;
    private String          status;
    private String          session;
    private String          agendaNumber;
    private String          headSignature;
    private String          publicationDate;

}
