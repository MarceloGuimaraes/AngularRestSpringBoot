package br.com.guimasoftware.repository.implement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import br.com.guimasoftware.model.Pessoa;
import br.com.guimasoftware.repository.PessoaRepositoryQuery;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	/*
	 * (non-Javadoc)
	 * Result: update pessoa set ativo=? where id=?
	 */
	@Transactional
	@Override
	public void atualizarAtivo(Long id, Boolean ativo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaUpdate<Pessoa> criteria = builder.createCriteriaUpdate(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		Metamodel m = manager.getMetamodel();
		EntityType<Pessoa> Pessoa_ = m.entity(Pessoa.class);
		
		/*criteria.set(root.get(Pessoa_.getAttribute("ativo").)
		criteria.set(Pessoa_.ativo, ativo).where(builder.equal(root.<Long>get(Pessoa_.id), id));*/
		Query query = manager.createQuery(criteria);
		if(query.executeUpdate() != 1) {
			throw new EmptyResultDataAccessException(1);
		}
	}

}
