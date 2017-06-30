
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.SearchEngine;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed Repository --------------------------------------
	@Autowired
	private UserRepository		userRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	accountService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private AbuseReportService	reportService;

	@Autowired
	private SearchEngineService	searchEngineService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods-------------------------------------------------------------------

	public User create() {
		User result;
		Authority authority;

		result = new User();
		this.actorService.setActorProperties(result);

		authority = new Authority();
		authority.setAuthority("ANIMANIAC");

		result.getUserAccount().addAuthority(authority);

		result.setAddress("");
		result.setGenre("");
		result.setPicture("");

		return result;
	}

	public User save(final User user) {
		User result;
		SearchEngine searchEngine;

		Assert.notNull(user, "user.error.null");

		user.setUserAccount(this.accountService.save(user.getUserAccount()));
		result = this.userRepository.save(user);
		Assert.notNull(result, "user.error.commit");
		if (user.getId() == 0) {
			searchEngine = this.searchEngineService.create(result.getId());
			this.searchEngineService.saveParaCreate(searchEngine);
			this.folderService.createBasicsFolders(result);
		}

		return result;

	}
	public void delete() {
		User user;
		user = this.findUserByPrincipal();
		this.reportService.deleteFromUser(user);
		this.searchEngineService.deleteFromUser(user);
		this.actorService.deleteFromActor(user);
		this.userRepository.delete(user);
		this.accountService.delete(user.getUserAccount().getId());
	}
	public User findOne(final int actorId) {

		return this.userRepository.findOne(actorId);
	}

	public Collection<User> findAll() {
		return this.userRepository.findAll();
	}

	public Long count() {
		return this.userRepository.count();
	}

	public void flush() {
		this.userRepository.flush();
	}
	// other business methods --------------------------------------

	public void ban(final int userId) {
		final List<Authority> auths = new ArrayList<Authority>(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities());
		final Authority auth = auths.get(0);
		Assert.isTrue(auth.getAuthority().equals("ADMINISTRATOR"), "user.error.notadmin");
		final UserAccount ua = this.userRepository.findOne(userId).getUserAccount();
		ua.setEnabled(false);
		this.accountService.save(ua);
		//this.messageService.alertUsers(userId);

	}

	public void unban(final int userId) {
		final List<Authority> auths = new ArrayList<Authority>(this.actorService.findActorByPrincipal().getUserAccount().getAuthorities());
		final Authority auth = auths.get(0);
		Assert.isTrue(auth.getAuthority().equals("ADMINISTRATOR"), "user.error.notadmin");
		final UserAccount ua = this.userRepository.findOne(userId).getUserAccount();
		ua.setEnabled(true);
		this.accountService.save(ua);

	}

	public User findUserByPrincipal() {
		User result;
		result = this.userRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public User reconstructNewUser(final UserForm userForm, final BindingResult binding) {
		User result;
		Md5PasswordEncoder encoder;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.actorService.setReconstructNewActorProperties(result, userForm);
		this.setReconstructProperties(result, userForm);

		this.validator.validate(result, binding);
		result.getUserAccount().setPassword(encoder.encodePassword(userForm.getUserAccount().getPassword(), null));
		return result;
	}

	public User reconstruct(final UserForm userForm, final User user, final BindingResult binding) {
		Md5PasswordEncoder encoder;
		User result;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.actorService.setReconstructActorProperties(result, user, userForm);
		this.setReconstructProperties(result, userForm);

		this.validator.validate(result, binding);

		result.getUserAccount().setPassword(encoder.encodePassword(userForm.getUserAccount().getPassword(), null));

		return result;
	}

	private void setReconstructProperties(final User result, final UserForm userForm) {

		result.setPicture(userForm.getPicture());
		result.setGenre(userForm.getGenre());
		result.setAddress(userForm.getAddress());

	}

	public void saveRate(final User user) {
		this.userRepository.save(user);
	}

	public void deleteBanned(final int userId) {
		User user;
		Assert.isTrue(this.actorService.findActorByPrincipal().getClass().equals(Administrator.class), "Debes ser administrador para esta acción");
		Assert.isTrue(userId != 0);
		user = this.findOne(userId);
		Assert.isTrue(user.getBanned(), "El usero debe estar banneado");

		this.deleteByAdmin(userId);
	}
	private void deleteByAdmin(final int userId) {
		User user;
		user = this.findOne(userId);
		this.reportService.deleteFromUser(user);
		this.searchEngineService.deleteFromUser(user);
		this.actorService.deleteFromActor(user);
		this.userRepository.delete(user);
		this.accountService.delete(user.getUserAccount().getId());
	}

	Collection<User> searchUser(final String name) {
		return this.userRepository.searchUser(name);

	}

}
