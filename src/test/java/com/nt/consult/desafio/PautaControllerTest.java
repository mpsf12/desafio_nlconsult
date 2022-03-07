package com.nt.consult.desafio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.nt.consult.desafio.exception.PautaNotFoundException;
import com.nt.consult.desafio.exception.SessaoNaoFinalizadaException;
import com.nt.consult.desafio.exception.SessaoNotFoundException;
import com.nt.consult.desafio.model.Sessao;
import com.nt.consult.desafio.model.User;
import com.nt.consult.desafio.model.Votacao;
import com.nt.consult.desafio.repository.SessaoRepository;
import com.nt.consult.desafio.repository.VotacaoRepository;
import com.nt.consult.desafio.util.GeraCpfCnpj;
import com.nt.consult.desafio.util.ResultadoPautaEnum;
import com.nt.consult.desafio.util.VotacaoEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nt.consult.desafio.controller.PautaController;
import com.nt.consult.desafio.model.Pauta;
import com.nt.consult.desafio.repository.PautaRepository;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(MockitoExtension.class)
public class PautaControllerTest {

	private MockMvc mvc;

	@InjectMocks
	private PautaController pautaController;


	@Mock
	private PautaRepository pautaRepository;
	@Mock
	private VotacaoRepository votacaoRepository;
	@Mock
	private SessaoRepository sessaoRepository;

	private JacksonTester<Pauta> pautaJacksonTester;

	@BeforeEach
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());

		mvc = MockMvcBuilders.standaloneSetup(pautaController)
				.build();
	}

	@Test
	void shouldFindPautaById() throws Exception {
		Pauta pauta = new Pauta(123L, "Descricao teste 1");

		Mockito.when(pautaRepository.findById(123L)).thenReturn(Optional.of(pauta));

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		MockHttpServletResponse response = result.getResponse();
		Assertions.assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
		Assertions.assertTrue(response.getContentAsString().equals(pautaJacksonTester.write(pauta).getJson()));
	}

	@Test
	void shouldNotFindPautaById() throws Exception {
		Pauta pauta = new Pauta(123L, "Descricao teste 1");

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResolvedException() instanceof PautaNotFoundException);

		MockHttpServletResponse response = result.getResponse();
		Assertions.assertTrue(response.getStatus() == HttpServletResponse.SC_NOT_FOUND);
	}

	//Gera um voto aleatorio, Sim para um numero par e Nao para um numero impar
	private VotacaoEnum geraVotoRandom(){
		Random random = new Random();
		if(random.nextInt() % 2 == 0){
			return VotacaoEnum.Sim;
		} else {
			return VotacaoEnum.Nao;
		}
	}
	@Test
	void deveContabilizarVotacaoPauta() throws Exception{
		Pauta pauta = new Pauta(123L, "Descricao teste 1");
		Sessao sessao = new Sessao(123L,System.currentTimeMillis() - 1000, 600, pauta);
		List<Votacao> votacaoList = new ArrayList<>();
		int votosSim = 0, votosNao = 0;
		for (long i = 0; i < 10; i++) {
			VotacaoEnum voto = geraVotoRandom();
			Votacao votacao = new Votacao(100L + i,pauta, new User(1L + i, "Teste " + i, GeraCpfCnpj.cpf()), voto);
			if(voto == VotacaoEnum.Sim){
				votosSim++;
			} else {
				votosNao++;
			}
			votacaoList.add(votacao);
		}

		Mockito.when(pautaRepository.findById(123L)).thenReturn(Optional.of(pauta));
		Mockito.when(sessaoRepository.findByPauta(pauta)).thenReturn(Optional.of(sessao));
		Mockito.when(votacaoRepository.findByPauta(pauta)).thenReturn(votacaoList);

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/contabilizar/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_OK);
		ResultadoPautaEnum resultadoResponse = ResultadoPautaEnum.valueOf(result.getResponse().getContentAsString().substring(1,result.getResponse().getContentAsString().length()-1));
		if(votosSim > votosNao){
			Assertions.assertTrue(resultadoResponse == ResultadoPautaEnum.Aprovado);
		} else {
			Assertions.assertTrue(resultadoResponse == ResultadoPautaEnum.Reprovado);
		}
	}

	@Test
	void contabilizarPautaComIdErrado() throws Exception{

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/contabilizar/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_NOT_FOUND);
		Assertions.assertTrue(result.getResolvedException() instanceof PautaNotFoundException);
	}

	@Test
	void contabilizarPautaComSessaoNaoExistente() throws Exception{
		Pauta pauta = new Pauta(123L, "Descricao teste 1");
		Mockito.when(pautaRepository.findById(123L)).thenReturn(Optional.of(pauta));

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/contabilizar/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_NOT_FOUND);
		Assertions.assertTrue(result.getResolvedException() instanceof SessaoNotFoundException);
	}

	@Test
	void contabilizarPautaComSessaoNaoFinalizada() throws Exception {
		Pauta pauta = new Pauta(123L, "Descricao teste 1");
		Mockito.when(pautaRepository.findById(123L)).thenReturn(Optional.of(pauta));

		Sessao sessao = new Sessao(123L,System.currentTimeMillis(), 600, pauta);
		Mockito.when(sessaoRepository.findByPauta(pauta)).thenReturn(Optional.of(sessao));

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.get("/api/pauta/contabilizar/123")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_NOT_FOUND);
		Assertions.assertTrue(result.getResolvedException() instanceof SessaoNaoFinalizadaException);
	}

	@Test
	void deveSalvarAPauta() throws Exception {
		Pauta pauta = new Pauta(123L, "Descricao teste 1");
		Mockito.when(pautaRepository.save(Mockito.any(Pauta.class))).thenAnswer(i -> i.getArguments()[0]);

		String jsontPauta = pautaJacksonTester.write(pauta).getJson();

		MvcResult result = mvc.perform( MockMvcRequestBuilders
						.post("/api/pauta/")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(jsontPauta))
				.andDo(print())
				.andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == HttpServletResponse.SC_OK);
		Assertions.assertTrue(result.getResponse().getContentAsString().equals(pautaJacksonTester.write(pauta).getJson()));
	}
	
}
