package com.nt.consult.desafio.dto;

import com.nt.consult.desafio.enums.VotacaoEnum;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.User;
import com.nt.consult.desafio.model.Votacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

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
        this.userId = user.getId();
        this.voto = voto;
    }

    public VotacaoDto(Votacao votacao){
        BeanUtils.copyProperties(votacao, this);
        this.pautaId = votacao.getPauta().getId();
        this.userId = votacao.getUser().getId();
    }
}
