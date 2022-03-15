package com.nt.consult.desafio.dto;

import com.nt.consult.desafio.enums.VotacaoEnum;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoDto {

    private long id;
    private long pautaId;
    private long userId;
    private VotacaoEnum voto;

    public VotacaoDto(Pauta pauta, User user, VotacaoEnum voto) {
        super();
        this.pautaId = pauta.getId();
        this.userId = getUserId();
        this.voto = voto;
    }
}
