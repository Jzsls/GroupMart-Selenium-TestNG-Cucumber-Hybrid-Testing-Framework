package groupmart.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	public static ExtentReports getReportObject() {
		String reportPath = System.getProperty("user.dir") + "//Reports/testng.html";
		// ExtentSparkReport class
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");

		// ExtentReports class responsible for all reporting executions (main class in
		// reporting)
		ExtentReports extent = new ExtentReports();
		// attaching the reporter created with ExtentSparkReporter class
		extent.attachReporter(reporter);
		// setting the tester name
		extent.setSystemInfo("Tester", "Zia Lodhi");
		// setting O.S info
		extent.setSystemInfo("OS Name", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		return extent;
	}

}
