
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	// Managed Repository --------------------------------------
	@Autowired
	private ActorRepository		actorRepository;

	// Supporting Services --------------------------------------

	@Autowired
	private UserAccountService	accountService;

	@Autowired
	private MessageService		messageService;
	@Autowired
	private CommentService		commentService;


	//Simple CRUD methods-------------------------------------------------------------------

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Long count() {
		return this.actorRepository.count();
	}
	// other business methods --------------------------------------

	public Actor findActorByPrincipal() {
		Actor result;
		result = this.actorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public void deleteFromActor(final Actor actor) {
		this.messageService.deleteFromActor(actor);
		this.commentService.deleteAllCommentsOfActor(actor);

	}

	public void setReconstructActorProperties(final Actor result, final Actor origin, final ActorForm actorForm) {
		UserAccount account;

		this.setReconstructNewActorProperties(result, actorForm);

		account = result.getUserAccount();
		// Setear lo que no viene del formulario:

		account.setId(origin.getUserAccount().getId());
		account.setVersion(origin.getUserAccount().getVersion());
		account.setAuthorities(origin.getUserAccount().getAuthorities());
		account.setEnabled(origin.getUserAccount().isEnabled());

		result.setId(origin.getId());
		result.setVersion(origin.getVersion());

	}

	public void setReconstructNewActorProperties(final Actor result, final ActorForm actorForm) {
		UserAccount account;

		account = result.getUserAccount();
		// Setear lo que viene del formulario:

		account.setPassword(actorForm.getUserAccount().getPassword());
		account.setUsername(actorForm.getUserAccount().getUsername());

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setEmail(actorForm.getEmail());
		result.setPhone(actorForm.getPhone());

	}

	public void setActorProperties(final Actor actor) {
		UserAccount userAccount;

		userAccount = this.accountService.create();

		actor.setEmail("");
		actor.setName("");
		actor.setPhone("");
		actor.setSurname("");
		actor.setUserAccount(userAccount);

	}

}
