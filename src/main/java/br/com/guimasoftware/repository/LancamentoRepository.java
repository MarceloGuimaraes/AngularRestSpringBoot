package br.com.guimasoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guimasoftware.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {


}
