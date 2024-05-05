import com.aventstack.extentreports.Status;
import com.evs.qacodes.utils.ApiUtils;
import com.evs.qacodes.utils.ExtentReport;


public class test {
	public static void main(String[] args) {
        ExtentReport.intItHtmlReport();
        ExtentReport.getModule("xyz");
        ExtentReport.startTest("Add User Profile", "To verify that user is able to add new users in system through API");
        ExtentReport.log(Status.INFO, "Get User Profiles");

        ApiUtils apiUtls = new ApiUtils();
		apiUtls.setupBasicAuthentication("https://ryadavqacodes.agilecrm.com/#", "ryadav@1secmail.com", "ryadav123#")
				.header("Accept", "application/json").expectedStatusCode(200)
				.makeRequest("get", "/dev/api/contacts/5397578904698880");

		System.out.println(apiUtls.name().asPrettyString());
		ExtentReport.endTest();

	}
}
