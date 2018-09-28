package br.com.marcus.apiaddress.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcus.apiaddress.model.Endereco;
import br.com.marcus.apiaddress.repository.EnderecoRepository;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Endereco> findAllEnderecos() {
		return enderecoRepository.findAll();
	}

	public Endereco findEnderecoById(Long id) {
		Optional<Endereco> opt = enderecoRepository.findById(id);

		Endereco endereco = opt.isPresent() ? opt.get() : null;

		return endereco;
	}
	
	public Endereco saveEndereco(Endereco endereco) {
		return enderecoRepository.save(endereco);
	}
	
	public void deleteEndereco(Long id) {
		enderecoRepository.deleteById(id);
	}
	
	public long countEnderecos() {
		return enderecoRepository.count();
	}

	public boolean isExist(long id) {
		return enderecoRepository.existsById(id);
	}

	

}
