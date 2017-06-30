
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.SearchEngine;

public interface SearchEngineRepository extends JpaRepository<SearchEngine, Integer> {

	@Query("select s from SearchEngine s where s.user.id = ?1")
	SearchEngine findByUser(int userId);

	@Query("select s from SearchEngine s where ?1 member of s.results")
	Collection<SearchEngine> findByResult(int actorId);

}
