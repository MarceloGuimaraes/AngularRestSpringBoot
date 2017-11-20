package br.com.guimasoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.guimasoftware.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {


}
