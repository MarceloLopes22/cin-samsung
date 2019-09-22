package br.com.desafio.cin.samsung.controles;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.enums.TipoEquipamento;
import br.com.desafio.cin.samsung.servicos.EquipamentoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EquipamentoResourceTest {

	@Autowired
	public WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper; 
	
	@Autowired
	private EquipamentoService service;
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
		mapper.setVisibility(mapper.getVisibilityChecker()
			     .withFieldVisibility(Visibility.ANY));
	}
	
	@Test
	public void testaCreateSucesso() {
		String url = "/api/equipamento/criar";
		
		File file = new File("https://cdn-motorshow-ssl.akamaized.net/wp-content/uploads/sites/2/2018/05/4_ms416_volkswagen-fox-xtreme1-747x420.jpg");
		Equipamento equipamento = new Equipamento(TipoEquipamento.AUTOMOTIVO, 
				"Fox Xtreme2019", 
				DateTimeFormatter.ofPattern("MM/yyyy").format(LocalDate.now()), 
				Double.valueOf(100000), file);
		String json = null;
		 try {
			 json = mapper.writeValueAsString(equipamento);
			mvc.perform(post(url)
				    .contentType(MediaType.APPLICATION_JSON)
				    .content(json)).andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testaCreateFalha() throws Exception {
		String url = "/api/equipamento/criar";
		
		Equipamento equipamento = new Equipamento(TipoEquipamento.AUTOMOTIVO, 
				"Fox Xtreme2019", 
				DateTimeFormatter.ofPattern("MM/yyyy").format(LocalDate.now()), 
				Double.valueOf(100000), null);
		String json = mapper.writeValueAsString(equipamento);
		 mvc.perform(post(url)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(json)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testaUpdateSucesso() throws Exception {
		String url = "/api/equipamento/atualizar";
		
		File file = new File("https://cdn-motorshow-ssl.akamaized.net/wp-content/uploads/sites/2/2018/05/4_ms416_volkswagen-fox-xtreme1-747x420.jpg");
		List<Equipamento> equipamentos = service.findAll(0, 10).getContent();
		Equipamento equipamento = equipamentos.get(equipamentos.size()-1);
		equipamento.setFoto(file);
		String json = mapper.writeValueAsString(equipamento);
		 mvc.perform(put(url)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(json)).andExpect(status().isOk());
	}
	
	@Test
	public void testaUpdateFalha() throws Exception {
		String url = "/api/equipamento/atualizar";
		
		List<Equipamento> equipamentos = service.findAll(0, 10).getContent();
		Equipamento equipamento = equipamentos.get(equipamentos.size()-1);
		equipamento.setMesano(null);
		String json = mapper.writeValueAsString(equipamento);
		 mvc.perform(put(url)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(json)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testaFindByIdSucesso() throws Exception {
		String url = "/api/equipamento/";
		
		List<Equipamento> list = service.findAll(0, 10).getContent();
		Equipamento equipamento = list.get(list.size()-1);
		url = url.concat(equipamento.getId_equipamento().toString());
		
		this.mvc.perform(get(url))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.id_equipamento", is(equipamento.getId_equipamento().intValue())));
	}
	
	@Test
	public void testaFindByIdFalha() throws Exception {
		String url = "/api/equipamento/";
		
		List<Equipamento> list = service.findAll(0, 10000).getContent();
		Equipamento equipamento = list.get(list.size()-1);
		Long naoExiste = equipamento.getId_equipamento() + 1;
		url = url.concat(naoExiste.toString());
		
		this.mvc.perform(get(url))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testaDeleteSucesso() throws Exception {
		String url = "/api/equipamento/";
		
		List<Equipamento> lista = service.findAll(0, 10).getContent();
		Equipamento equipamento = lista.get(lista.size()-1);
		url = url.concat(equipamento.getId_equipamento().toString());
		
		this.mvc.perform(delete(url))
		.andExpect(status().isOk());
		
		Optional<Equipamento> findByIdEquipamento = service.findByIdEquipamento(equipamento.getId_equipamento());
		
		assertEquals(Boolean.FALSE, findByIdEquipamento.isPresent());
	}
	
	@Test
	public void testaDeleteFolha() throws Exception {
		String url = "/api/equipamento/null";
		
		this.mvc.perform(delete(url))
		.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString().contains("IdEquipamento = null  não pode ser nulo.");
	}
	
	@Test
	public void testaFindAllRetornoOk() throws Exception {
		this.mvc.perform(get("/api/equipamento/0/10"))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testaFindAllSucesso() throws Exception {
		Page<Equipamento> all = service.findAll(0, 10);
		assertNotNull(all.getContent());
	}
	
	@Test
	public void testaFindAllRetornoFalha() throws Exception {
		MvcResult andReturn = this.mvc.perform(get("/api/equipamento/0/0"))
		.andExpect(status().isBadRequest()).andReturn();
		andReturn.getResponse().getContentAsString().contains("Falha na paginação");
	}
	

}
