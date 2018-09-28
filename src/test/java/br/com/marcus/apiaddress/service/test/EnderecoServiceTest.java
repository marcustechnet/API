package br.com.marcus.apiaddress.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.marcus.apiaddress.model.Endereco;
import br.com.marcus.apiaddress.repository.EnderecoRepository;
import br.com.marcus.apiaddress.service.EnderecoServiceImpl;

public class EnderecoServiceTest {

	@InjectMocks
	private EnderecoServiceImpl service;

	@Mock
	private EnderecoRepository enderecoRepository;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFindEnderecoById() {
		Endereco endereco = new Endereco(1L, "Rua Marte", 429, "06414-000", "Barueri", "SP");
		Optional<Endereco> optionalEndereco = Optional.of(endereco);

		when(enderecoRepository.findById(1L)).thenReturn(optionalEndereco);

		assertEquals(endereco, service.findEnderecoById(1L));
	}

	@Test
	public void testSave() {
		Endereco endereco = new Endereco(1L, "Rua Marte", 429, "06414-000", "Barueri", "SP");
		when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

		assertEquals(1l, service.saveEndereco(endereco).getId().longValue());
	}

}
