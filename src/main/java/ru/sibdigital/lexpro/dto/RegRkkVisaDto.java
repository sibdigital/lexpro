package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sibdigital.lexpro.model.ClsRkkStage;
import ru.sibdigital.lexpro.model.RegRkkVisa;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegRkkVisaDto {
    private Long    id;
    private Long    regRkkVisaId;
    private String  date;
    private String  note;
    private Long    stageId;

    public RegRkkVisaDto(RegRkkVisa regRkkVisa) {
        if (regRkkVisa != null) {
            this.setRegRkkVisaId(regRkkVisa.getId());
            this.setNote(regRkkVisa.getNote());
            this.setDate(regRkkVisa.getDate().toString());

            ClsRkkStage stage = regRkkVisa.getStage();
            if (stage != null) {
                this.setStageId(stage.getId());
            }
        }
    }
}
