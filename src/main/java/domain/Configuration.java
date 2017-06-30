
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private long	cacheTime;


	@Min(0)
	public long getCacheTime() {
		return this.cacheTime;
	}

	public void setCacheTime(final long cacheTime) {
		this.cacheTime = cacheTime;
	}

	// Relationships ----------------------------------------------------------

}
