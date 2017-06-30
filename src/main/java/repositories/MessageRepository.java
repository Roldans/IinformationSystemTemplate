
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Esto devuelve todo los mensajes de una carpeta, ya sean enviados por el dueño o no (incluye los recividos).
	@Query("select m from Message m where m.folder.id=?1")
	List<Message> findMessagesInFolder(int folderId);
	//Esto devuelve todos los mensajes del actor,ya sean enviados por el o no (incluye los recividos).
	@Query("select m from Message m where m.folder.actor.id=?1")
	List<Message> findMessagesOfActor(int actorId);

	//--------->>>>>>>>>>>>USADOS PARA EL BORRADO DE CUENTA<<<<<<<<<<<<<<<<<<<<<<<<<
	//Esto evuelve todos los mensajes en los que el actor es el "Sender"
	@Query("select m from Message m where m.sender.id=?1")
	List<Message> findMessageWithSender(int actorId);
	//Esto evuelve todos los mensajes en los que el actor es el "Recipient"
	@Query("select m from Message m where m.recipient.id=?1")
	List<Message> findMessageWithRecipient(int actorId);
	//>>>>>>>>>>>>USADOS PARA EL BORRADO DE CUENTA<<<<<<<<<<<<---------------

}
