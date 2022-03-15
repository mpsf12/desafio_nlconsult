package com.nt.consult.desafio.dto;

import com.nt.consult.desafio.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

    private long id;
    private String descricao;

    public PautaDto(Pauta pauta){
        BeanUtils.copyProperties(pauta, this);
    }
}
