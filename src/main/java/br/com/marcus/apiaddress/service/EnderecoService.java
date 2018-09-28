package br.com.marcus.apiaddress.service;

import java.util.List;

import br.com.marcus.apiaddress.model.Endereco;

public interface EnderecoService {
	
	public List<Endereco> findAllEnderecos();

	public Endereco findEnderecoById(Long id);

	public Endereco saveEndereco(Endereco endereco);

	public void deleteEndereco(Long id);

	public long countEnderecos();

	public boolean isExist(long id);
}
