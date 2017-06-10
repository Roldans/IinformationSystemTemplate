
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

	//Find all the sendt chirps for a given actor
	@Query("select a from Attachment a where a.chirp.id=?1")
	public List<Attachment> findAttachmentsOfChirp(int chirpId);

	//Find all the sendt chirps for a given actor
	@Query("select a from Attachment a where (a.chirp.sender.id=?1 and a.chirp.isSender=true) or (a.chirp.recipient.id=?1 and a.chirp.isSender=false)")
	public Collection<Attachment> findAttachmentsOfChorbiDeleting(int id);

}
