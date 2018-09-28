package br.com.marcus.apiaddress.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcus.apiaddress.model.Endereco;


public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
