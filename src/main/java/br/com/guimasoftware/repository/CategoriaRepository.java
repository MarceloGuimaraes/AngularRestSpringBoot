package br.com.guimasoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.guimasoftware.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


}
