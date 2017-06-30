
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamWordRepository;
import domain.Actor;
import domain.Administrator;
import domain.SpamWord;

@Service
@Transactional
public class SpamWordService {

	// Managed Repository --------------------------------------
	@Autowired
	private SpamWordRepository	spamWordRepository;

	// Other Services --------------------------------------
	@Autowired
	private ActorService		actorService;


	public SpamWord create() {
		final SpamWord result = new SpamWord();
		return result;
	}

	public Collection<SpamWord> findAll() {
		Collection<SpamWord> result = new ArrayList<SpamWord>();
		result = this.spamWordRepository.findAll();
		return result;
	}

	public SpamWord findOne(final int spamWordId) {
		SpamWord result;
		result = this.spamWordRepository.findOne(spamWordId);
		Assert.notNull(result, "no se ha encontrado el objeto");
		return result;
	}

	public void delete(final int spamWordId) {
		Assert.isTrue(this.actorService.findActorByPrincipal().getClass().equals(Administrator.class), "Debes ser un administrador para hacer esta acccion");
		Assert.isTrue(spamWordId != 0, "La palabra de spam debe existir");
		this.spamWordRepository.delete(spamWordId);
	}

	public SpamWord save(final SpamWord spamWord) {
		SpamWord result;
		SpamWord existing;
		Actor actor;
		actor = this.actorService.findActorByPrincipal();
		Assert.notNull(spamWord, "spamWord.error.nullSpamWord");
		Assert.isTrue(Administrator.class.equals(actor.getClass()), "spamWord.error.notAdmin");
		Assert.notNull(spamWord.getWord(), "spamWord.error.nullWord");
		Assert.isTrue(!spamWord.getWord().isEmpty(), "La palabra no debe estar vacia");
		existing = this.spamWordRepository.findByWord(spamWord.getWord());
		if (existing != null)
			Assert.isTrue(!existing.getWord().equals(spamWord.getWord()), "spamWord.error.alreadyExists");

		result = this.spamWordRepository.save(spamWord);
		return result;
	}
	//Simple CRUD methods-------------------------------------------------------------------

	public void flush() {
		this.spamWordRepository.flush();

	}

	// other business methods --------------------------------------

}
