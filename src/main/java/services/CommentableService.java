
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CommentableRepository;
import domain.Commentable;

@Service
@Transactional
public class CommentableService {

	// Managed Repository --------------------------------------
	@Autowired
	private CommentableRepository	commentableRepository;


	// Supporting Services --------------------------------------

	//Simple CRUD methods-------------------------------------------------------------------

	public Commentable findOne(final int commentableId) {
		this.commentableRepository.findAll();
		return this.commentableRepository.findOne(commentableId);
	}

	// other business methods --------------------------------------

}
