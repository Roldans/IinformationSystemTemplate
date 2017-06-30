
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.AbuseReport;

public interface AbuseReportRepository extends JpaRepository<AbuseReport, Integer> {

	@Query("select a from AbuseReport a where a.reported.id = ?1")
	Collection<AbuseReport> findAbuseReportRecived(int id);

	@Query("select a from AbuseReport a where a.reporter.id = ?1")
	Collection<AbuseReport> findAbuseReportDone(int id);

	@Query("select a from AbuseReport a order by a.reportDate desc")
	Collection<AbuseReport> findAllOrderedByDate();

}
