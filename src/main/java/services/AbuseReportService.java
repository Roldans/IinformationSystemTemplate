
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AbuseReportRepository;
import domain.AbuseReport;
import domain.User;

@Service
@Transactional
public class AbuseReportService {

	//Managed Repository--------------------------------------------------------------------
	@Autowired
	private AbuseReportRepository	abuseReportRepository;

	//Supported Services--------------------------------------------------------------------
	@Autowired
	private UserService				userService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods------------------------------------------------------------
	public AbuseReport create(final int reportedId) {
		AbuseReport result;

		result = new AbuseReport();
		result.setReported(this.userService.findOne(reportedId));

		return result;
	}

	public AbuseReport save(final AbuseReport abuseReport) {
		AbuseReport result;

		Assert.isTrue(!abuseReport.getReporter().equals(abuseReport.getReported()), "abuseReport.cant.report.myself");

		result = this.abuseReportRepository.save(abuseReport);
		return result;
	}

	public Collection<AbuseReport> findAll() {
		return this.abuseReportRepository.findAllOrderedByDate();
	}

	public void flush() {
		this.abuseReportRepository.flush();
	}

	//Other Bussnisnes methods------------------------------------------------------------

	public AbuseReport reconstruct(final AbuseReport abuseReport, final BindingResult binding) {
		AbuseReport result;
		User principal;

		result = this.create(abuseReport.getReported().getId());
		principal = this.userService.findUserByPrincipal();

		result.setReporter(principal);
		result.setDescription(abuseReport.getDescription());
		result.setReportDate(new Date(System.currentTimeMillis() - 100));

		this.validator.validate(result, binding);

		return result;

	}

	public void deleteFromUser(final User user) {
		Collection<AbuseReport> reportsRecived;
		Collection<AbuseReport> reportsDone;

		reportsRecived = this.abuseReportRepository.findAbuseReportRecived(user.getId());
		reportsDone = this.abuseReportRepository.findAbuseReportDone(user.getId());

		this.abuseReportRepository.delete(reportsRecived);

		for (final AbuseReport report : reportsDone) {
			report.setReporter(null);
			this.abuseReportRepository.save(report);
		}
	}

}
