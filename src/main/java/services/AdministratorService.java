
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed Repository --------------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting Services --------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UserAccountService		accountService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods-------------------------------------------------------------------

	public Administrator create() {
		Administrator result;
		Authority authority;

		result = new Administrator();
		this.actorService.setActorProperties(result);

		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");

		result.getUserAccount().addAuthority(authority);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Administrator result;

		Assert.notNull(administrator, "administrator.error.null");

		administrator.setUserAccount(this.accountService.save(administrator.getUserAccount()));
		result = this.administratorRepository.save(administrator);
		Assert.notNull(result, "administrator.error.commit");
		return result;

	}

	public Administrator findOne(final int actorId) {
		return this.administratorRepository.findOne(actorId);
	}

	// other business methods --------------------------------------

	public Administrator findAdministratorByPrincipal() {
		Administrator result;
		result = this.administratorRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		return result;
	}

	public Administrator reconstruct(final AdministratorForm administratorForm, final Administrator administrator, final BindingResult binding) {
		Md5PasswordEncoder encoder;
		Administrator result;

		result = this.create();
		encoder = new Md5PasswordEncoder();

		this.actorService.setReconstructActorProperties(result, administrator, administratorForm);
		this.setReconstructProperties(result, administratorForm);

		this.validator.validate(result, binding);

		result.getUserAccount().setPassword(encoder.encodePassword(administratorForm.getUserAccount().getPassword(), null));

		return result;
	}

	private void setReconstructProperties(final Administrator result, final AdministratorForm administratorForm) {

	}

}
