package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


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
    private Long            lawSubject; // id of Subject
    private Long            speaker; // id of Speaker
    private Boolean         readyForSession;
    private String          deadline;
    private String          includedInAgenda;
    private Long            responsibleOrganization; // id of Organization
    private Long            responsibleEmployee; // id of Employee
    private Long            status;
    private Long            session;
    private String          agendaNumber;
    private String          headSignature;
    private String          publicationDate;
}
