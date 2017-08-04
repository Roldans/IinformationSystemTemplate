
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SearchEngineRepository;
import domain.SearchEngine;
import domain.User;

@Service
@Transactional
public class SearchEngineService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private SearchEngineRepository	searchEngineRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserService				userService;

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private Validator				validator;


	//Simple CRUD methods-------------------------------------------------------------------

	public SearchEngine create(final int userId) {
		SearchEngine result;
		final Collection<User> results = new ArrayList<User>();
		result = new SearchEngine();
		result.setUser(this.userService.findOne(userId));
		result.setResults(results);
		result.setSearchMoment(new Date(System.currentTimeMillis() - this.configurationService.findConfiguration().getCacheTime()));

		return result;
	}
	public SearchEngine findOne(final int searchEngineId) {
		SearchEngine result;
		result = this.searchEngineRepository.findOne(searchEngineId);
		return result;
	}

	public SearchEngine saveParaCreate(final SearchEngine searchEngine) {
		return this.searchEngineRepository.save(searchEngine);
	}

	public SearchEngine save(final SearchEngine searchEngine) {
		SearchEngine result;
		final Collection<User> users;
		Date timeOfCache, lastSearch;

		//Revisar que el search guardado sea del Principal
		Assert.isTrue(User.class.equals(this.actorService.findActorByPrincipal().getClass()));
		Assert.isTrue(this.userService.findUserByPrincipal().equals(searchEngine.getUser()));

		//Fechas para comprobar el tiempo de caché

		timeOfCache = new DateTime(System.currentTimeMillis() - this.configurationService.findConfiguration().getCacheTime()).toDate();
		lastSearch = new DateTime(searchEngine.getSearchMoment().getTime()).toDate();

		result = searchEngine;

		//Comprobamos si el SearchTemplate ha sido modificado
		if (lastSearch.before(timeOfCache) || this.searchTemplateHasBeenModified(searchEngine)) {

			result.setSearchMoment(new DateTime(System.currentTimeMillis() - 1000).toDate());

			//Busqueda en base de Datos

			users = this.userService.searchUser(searchEngine.getName());
			result.setResults(users);

			result = this.searchEngineRepository.save(result);
		}

		Assert.notNull(result);

		return result;
	}
	//Comprueba si ha sido modificado el searchTemplate
	private boolean searchTemplateHasBeenModified(final SearchEngine searchEngine) {
		SearchEngine old;
		boolean result;
		old = this.searchEngineRepository.findOne(searchEngine.getId());

		result = !searchEngine.getName().equals(old.getName());

		return result;

	}

	public void flush() {
		this.searchEngineRepository.flush();
	}

	//Other Business methods-------------------------------------------------------------------

	public void deleteFromUser(final User user) {
		SearchEngine searchEngine;

		searchEngine = this.searchEngineRepository.findByUser(user.getId());

		this.searchEngineRepository.delete(searchEngine);
	}

	public SearchEngine findSearchEngineByUser(final int userId) {
		return this.searchEngineRepository.findByUser(userId);
	}
	public SearchEngine findByPrincipal() {
		final SearchEngine result;
		final int actorId = this.actorService.findActorByPrincipal().getId();
		result = this.findSearchEngineByUser(actorId);
		return result;
	}
	public void deleteFromResult(final User user) {
		Collection<SearchEngine> searchEngines;

		searchEngines = this.searchEngineRepository.findByResult(user.getId());

		for (final SearchEngine engine : searchEngines)
			engine.getResults().remove(user);

		this.searchEngineRepository.save(searchEngines);
	}

	public SearchEngine reconstruct(final SearchEngine searchEngine, final BindingResult binding) {
		SearchEngine res, old;

		old = this.findOne(searchEngine.getId());
		res = this.create(this.actorService.findActorByPrincipal().getId());

		//old things
		res.setId(old.getId());
		res.setVersion(old.getVersion());
		res.setSearchMoment(old.getSearchMoment());
		res.setResults(old.getResults());
		res.setUser(old.getUser());

		//New things
		res.setName(searchEngine.getName());
		this.validator.validate(res, binding);

		return res;
	}
}
