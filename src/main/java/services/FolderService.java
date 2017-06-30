
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	//Manage Repository-------------------------------

	@Autowired
	private FolderRepository	folderRepository;

	//Supporting Services-----------------------------

	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageService		messageService;


	//Constructors------------------------------------

	//Simple CRUD methods-----------------------------

	public Folder create(final Actor actor) {
		final Folder result = new Folder();
		result.setActor(actor);

		return result;
	}

	public Folder save(final Folder folder) {
		final Actor principal = this.actorService.findActorByPrincipal();
		Assert.notNull(folder, "SAVE: El folder ha de ser NO nulo");
		Assert.notNull(folder.getActor(), "El actor no puede ser nulo");
		Assert.isTrue(principal.equals(folder.getActor()), "SAVE: UserAccount no valido");
		Assert.isTrue(!folder.getName().isEmpty(), "El nombre no puede estar vacio");
		Assert.isTrue(null == this.folderRepository.findAFolderOfActor(principal.getId(), folder.getName()), "folder.error.nameUsed");
		Assert.isTrue(!folder.getReadOnly() || folder.getId() == 0, "No se puede editar un folder onlyRead");

		final Folder result = this.folderRepository.save(folder);
		return result;
	}
	public Collection<Folder> save(final Collection<Folder> folders) {
		//final UserAccount principal = LoginService.getPrincipal();
		final Collection<Folder> result;
		Assert.notNull(folders, "SAVE: El folder ha de ser NO nulo");

		result = this.folderRepository.save(folders);
		return result;
	}
	public Collection<Folder> findAll() {
		final Collection<Folder> result = this.folderRepository.findAll();
		return result;
	}

	public Folder findOne(final Integer folderId) {
		Assert.notNull(folderId, "la id no puede ser nula");
		Assert.isTrue(folderId != 0, "la id no puede ser 0");
		final Folder result = this.folderRepository.findOne(folderId);
		return result;
	}
	public void delete(final Folder folder) {
		final Actor principal = this.actorService.findActorByPrincipal();
		Collection<Message> messages;
		Assert.notNull(folder, "DELETE: El folder ha de ser NO nulo");
		Assert.isTrue(folder.getId() != 0, "El folder ha de haber sido guardado");
		Assert.isTrue(principal.equals(folder.getActor()), "DELETE: UserAccount no valido");
		Assert.isTrue(!folder.getReadOnly(), "Los folder basicos NO pueden ser borrados");
		messages = this.messageService.findMessagesInFolder(folder.getId());
		this.messageService.deleteAll(messages);
		this.folderRepository.delete(folder);
	}

	//Other business methods--------------------------
	public void createBasicsFolders(final Actor actor) {
		Folder folder;
		final String[] names = {
			"inbox", "outbox", "trashbox", "spambox"
		};

		for (int i = 0; i < 4; i++) {
			folder = this.create(actor);
			folder.setReadOnly(true);
			folder.setName(names[i]);
			this.folderRepository.save(folder);
		}
	}
	//Busca un Folder con el nombre "name" del actor "actor"
	//	public Folder findFolderFromActor(Actor actor, String name) {
	//	
	//		Assert.notNull(actor);
	//		Assert.hasText(name);
	//		Collection<Folder> folders =findFoldersOfActor(actor);
	//		Folder result=null;
	//		for(Folder folder:folders){
	//			if(folder.getName()==name){
	//				result=folder;
	//				break;
	//			}
	//		}
	//		Assert.notNull(result);
	//		return result;
	//	}

	//busca todos los Folder del actor "actor"
	public Collection<Folder> findFoldersOfActor(final Actor actor) {

		final Collection<Folder> result = this.folderRepository.findFoldersOfActor(actor.getId());
		return result;
	}
	//busca un Folder del actor "actor"
	public Folder findFolderOfActor(final Actor actor, final String folderName) {
		final Folder result = this.folderRepository.findAFolderOfActor(actor.getId(), folderName);
		return result;
	}

	public void flush() {
		this.folderRepository.flush();

	}
}
