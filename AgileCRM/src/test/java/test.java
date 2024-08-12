import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.evs.qacodes.utils.ApiUtils;
import com.evs.qacodes.utils.ExtentReport;


public class test {
	@BeforeMethod
	public void start() {
		ExtentReport.intItHtmlReport();
        ExtentReport.startTest("Add User Profile", "To verify that user is able to add new users in system through API");

	}
	@Test
	public static void mainss() {
        ApiUtils apiUtls = new ApiUtils();
		apiUtls.setupBasicAuthentication("https://ryadavqacodes.agilecrm.com/#", "ryadav@1secmail.com", "ryadav123#")
				.header("Accept", "application/json").expectedStatusCode(200)
				.makeRequest("get", "/dev/api/contacts/5397578904698880");

		System.out.println(apiUtls.name().asPrettyString());

	}
	@AfterMethod
	public void end() {
		ExtentReport.endTest();

	}
}
