package br.com.marcus.apiaddress.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcus.apiaddress.model.Endereco;
import br.com.marcus.apiaddress.service.EnderecoService;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@PostMapping("/")
	public ResponseEntity<?> createEndereco(@Valid @RequestBody Endereco endereco) {

		endereco.setId(null);
		String msgError = enderecoValidation(endereco);
		
		if (msgError.isEmpty()){
			endereco = enderecoService.saveEndereco(endereco);
			return new ResponseEntity<String>("Endereço incluído com Sucesso!", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(msgError, HttpStatus.BAD_REQUEST);
		}

	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateEndereco(@PathVariable("id") Long id, @Valid @RequestBody Endereco endereco) {

		String msgError;

		if (enderecoService.isExist(id)) {
			msgError = enderecoValidation(endereco);

			if (msgError.isEmpty()) {
				endereco.setId(id);
				enderecoService.saveEndereco(endereco);

				return new ResponseEntity<String>("Endereço alterado com Sucesso!", HttpStatus.OK);
			}

		} else {
			return new ResponseEntity<String>("Endereço inexistente!!", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>(msgError, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/")
	public List<Endereco> getEnderecos() {
		return enderecoService.findAllEnderecos();
	}

	@GetMapping(value = "/{id}")
	public Endereco getEndereco(@PathVariable("id") Long id) {
		return enderecoService.findEnderecoById(id);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteEndereco(@PathVariable("id") Long id) {

		if (enderecoService.isExist(id)) {
			enderecoService.deleteEndereco(id);
			return new ResponseEntity<String>("Endereço excluído com Sucesso!", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("Endereço inexistente!!", HttpStatus.NOT_FOUND);
		}
	}	
	
	private String enderecoValidation(Endereco endereco) {

		StringBuilder msg = new StringBuilder("");

		if (endereco.getRua() == null || endereco.getRua().isEmpty()) {
			msg.append("Favor preencher o campo RUA.");
			msg.append(System.lineSeparator());
		}

		if (endereco.getNumero() == null) {
			msg.append("Favor preencher o campo NÚMERO.");
			msg.append(System.lineSeparator());
		}

		if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
			msg.append("Favor preencher o campo CEP.");
			msg.append(System.lineSeparator());
		}

		if (endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
			msg.append("Favor preencher o campo CIDADE.");
			msg.append(System.lineSeparator());
		}

		if (endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
			msg.append("Favor preencher o campo ESTADO.");
			msg.append(System.lineSeparator());
		}

		return msg.toString();
	}

}
