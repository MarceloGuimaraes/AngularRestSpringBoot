package br.com.guimasoftware.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guimasoftware.event.RecursoCriadoEvent;
import br.com.guimasoftware.model.Pessoa;
import br.com.guimasoftware.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Pessoa> pessoaList = pessoaRepository.findAll();
		// List<Pessoa> pessoaList = new ArrayList<Pessoa>();
		// //pessoaRepository.findAll();
		return !pessoaList.isEmpty() ? ResponseEntity.ok(pessoaList) : ResponseEntity.noContent().build();

	}

	/*
	 * Desta forma, a pessoa é salva (Criada) mas não é informado o retorno ao header do recurso criado.
	 * No método abaixo deste comentado, é informado ao header a criação do recurso 
	 * 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criar(@RequestBody Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}*/
	
	/*Quando criado alguma coisa no banco de dados, o Rest fala que nos headers de resposta deve contem um location para 
	informar como recupera o recurso que foi criado*/
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		
		/* Codigo comentado para utilização de event Listener 
		 * 
		 * URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(pessoaSalva.getCodigo()).toUri();
		 * response.setHeader("Location", uri.toASCIIString());
		 *	return ResponseEntity.created(uri).body(pessoaSalva);
		 */
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
	
	/*CAMPO Blob, salvar e buScar
	@PostMapping
	public void upload(@RequestParam("files[]") MultipartFile[] files) {
	  // salvar arquivo
	}
	
	
	@GetMapping
	public ResponseEntity<byte[]> download() {
	  byte[] bytes = null; // buscar os bytes de algum lugar

	  return ResponseEntity.ok()
	    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE) // aqui você coloca o mediaType coerente
	    .body(bytes);
	}*/
	
}