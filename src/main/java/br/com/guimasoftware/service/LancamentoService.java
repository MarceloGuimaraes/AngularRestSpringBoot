package br.com.guimasoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guimasoftware.exceptionhandler.PessoaInexistenteOuInativaException;
import br.com.guimasoftware.model.Lancamento;
import br.com.guimasoftware.model.Pessoa;
import br.com.guimasoftware.repository.LancamentoRepository;
import br.com.guimasoftware.repository.PessoaRepository;

@Service
public class LancamentoService {


	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private LancamentoRepository lancamentoRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	
	

	
	
}
