package br.com.marcus.apiaddress.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.marcus.apiaddress.controller.EnderecoController;
import br.com.marcus.apiaddress.model.Endereco;
import br.com.marcus.apiaddress.repository.EnderecoRepository;
import br.com.marcus.apiaddress.service.EnderecoService;

@RunWith(SpringRunner.class)
@WebMvcTest(EnderecoController.class)
@AutoConfigureDataJpa
public class EnderecoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EnderecoRepository enderecoRepository;
	
	@MockBean
	private EnderecoService service;

	private JacksonTester<Endereco> jsonEndereco;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	@Test
	public void searchByIdWhenExists() throws Exception {
		// given
		given(service.findEnderecoById(1L)).willReturn(new Endereco(1L, "Rua Marte", 429, "06414-000", "Barueri", "SP"));

		// when
		MockHttpServletResponse response = mvc.perform(get("/enderecos/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonEndereco.write(new Endereco(1L, "Rua Marte", 429, "06414-000", "Barueri", "SP")).getJson());
	}
	
	@Test
	public void searchByIdWhenDoesNotExist() throws Exception {
		// given		
		given(service.findEnderecoById(1L)).willReturn(new Endereco(1L, "Rua Marte", 429, "06414-000", "Barueri", "SP"));
		given(service.findEnderecoById(2L)).willReturn(null);

		// when
		MockHttpServletResponse response = mvc.perform(get("/enderecos/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEmpty();
	}
	
	@Test
	public void createNewEndereco() throws Exception {
		// when
		MockHttpServletResponse response = mvc
				.perform(post("/enderecos/").contentType(MediaType.APPLICATION_JSON).content(
						jsonEndereco.write(new Endereco("Rua Marte", 429, "06414-000", "Barueri", "SP")).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	public void createNewEnderecoWithoutARequiredField() throws Exception {
		//given
		Endereco endereco = new Endereco("Rua Marte", 429, "06414-000", "Barueri", "SP");
		endereco.setCidade(null);
		
		// when
		MockHttpServletResponse response = mvc
				.perform(post("/enderecos/").contentType(MediaType.APPLICATION_JSON).content(
						jsonEndereco.write(endereco).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		assertThat(response.getContentAsString().trim()).isEqualTo("Favor preencher o campo CIDADE.");
	}
	
	@Test
	public void updateEndereco() throws Exception {
		//given
		given(service.isExist(1L)).willReturn(true);
		
		// when
		MockHttpServletResponse response = mvc
				.perform(put("/enderecos/1").contentType(MediaType.APPLICATION_JSON).content(
						jsonEndereco.write(new Endereco("Rua Terra", 429, "06414-000", "Barueri", "SP")).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void updateWhenEnderecoDontExist() throws Exception {
		//given
		given(service.isExist(1L)).willReturn(true);
		
		// when
		MockHttpServletResponse response = mvc
				.perform(put("/enderecos/2").contentType(MediaType.APPLICATION_JSON).content(
						jsonEndereco.write(new Endereco("Rua Terra", 429, "06414-000", "Barueri", "SP")).getJson()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deleteEndereco() throws Exception {
		//given
		given(service.isExist(1L)).willReturn(true);
		
		// when
		MockHttpServletResponse response = mvc.perform(delete("/enderecos/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void deleteWhenEnderecoDontExist() throws Exception {
		//given
		given(service.isExist(2L)).willReturn(true);
		
		// when
		MockHttpServletResponse response = mvc.perform(delete("/enderecos/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

}
