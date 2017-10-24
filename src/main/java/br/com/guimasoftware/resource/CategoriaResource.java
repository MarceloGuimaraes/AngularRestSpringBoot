package br.com.guimasoftware.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.guimasoftware.model.Categoria;
import br.com.guimasoftware.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Categoria> categoriaList = categoriaRepository.findAll();
		// List<Categoria> categoriaList = new ArrayList<Categoria>();
		// //categoriaRepository.findAll();
		return !categoriaList.isEmpty() ? ResponseEntity.ok(categoriaList) : ResponseEntity.noContent().build();

	}

	/*
	 * Desta forma, a categoria é salva (Criada) mas não é informado o retorno ao header do recurso criado.
	 * No método abaixo deste comentado, é informado ao header a criação do recurso 
	 * 
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void criar(@RequestBody Categoria categoria) {
		categoriaRepository.save(categoria);
	}*/
	
	/*Quando criado alguma coisa no banco de dados, o Rest fala que nos headers de resposta deve contem um location para 
	informar como recupera o recurso que foi criado*/
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
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