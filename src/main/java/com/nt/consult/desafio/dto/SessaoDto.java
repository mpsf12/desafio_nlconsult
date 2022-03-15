package com.nt.consult.desafio.dto;

import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.model.Sessao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDto {

    private long id;
    private long aberturaSessao;
    private long duracao;
    private long pautaId;

    public SessaoDto(long aberturaSessao, long duracao, Pauta pauta) {
        this.aberturaSessao = aberturaSessao;
        this.duracao = duracao;
        this.pautaId = pauta.getId();
    }

    public SessaoDto(Sessao sessao){
        BeanUtils.copyProperties(sessao, this);
        this.pautaId = sessao.getPauta().getId();
    }
}
