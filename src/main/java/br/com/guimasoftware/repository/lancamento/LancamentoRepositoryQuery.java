package br.com.guimasoftware.repository.lancamento;

import java.util.List;

import br.com.guimasoftware.model.Lancamento;
import br.com.guimasoftware.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
