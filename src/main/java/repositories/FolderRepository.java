
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {

	//For a given actor, returs all his folders
	@Query("select f from Folder f where f.actor.id=?1")
	Collection<Folder> findFoldersOfActor(int actorId);
	//For a given actor, returs all his folders
	@Query("select f from Folder f where f.actor.id=?1 and f.name=?2")
	Folder findAFolderOfActor(int actorId, String name);

}
