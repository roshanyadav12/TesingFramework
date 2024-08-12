package com.evs.qacodes.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReport {
	private static ExtentReports extent;
	private static ExtentTest test;

	public static ExtentReports intItHtmlReport() {

		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("TestReport//testReport.html");

		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("xyz");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("sds");
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return extent;
	}

	static Map<String, ExtentTest> extentModulesMap = new HashMap<String, ExtentTest>();

	public static synchronized ExtentTest getModule(String module) {

		ExtentTest currentThread = extentModulesMap.get(module);

		if (currentThread == null) {
			return createModule(module);
		} else {
			return currentThread;
		}

	}

	public static synchronized ExtentTest createModule(String module) {
		test = extent.createTest(module);
		extentModulesMap.put(module, test);
		return test;
	}

	public static synchronized ExtentTest startTest(String module, String testCase, String... params) {
        ExtentTest extentModule =getModule(module);
		String testCaseDescription = MessageFormat.format(testCase, params);
		ExtentTest test =extentModule.createNode(testCaseDescription);
        set((Long) (Thread.currentThread().getId()), test);
		return test;
	}

	public static synchronized void endTest() {
		extent.flush();
	}

	public static synchronized void log(Status status, Object logObject) {

		if (logObject instanceof String) {
			test.log(status, (String) logObject);
		} else if (logObject instanceof Throwable) {
			test.log(status, MarkupHelper.createCodeBlock(((Throwable) logObject).getMessage()));
		} else if (logObject instanceof Media) {
			test.log(status, (Media) logObject);
		}

	}
	 private static Map<Long, ExtentTest> reporterMap = new HashMap<Long, ExtentTest>();

	    public static ExtentTest get(long l) {
	        return reporterMap.get(l);
	    }

	    public static void set(Long integer, ExtentTest test) {
	        reporterMap.put(integer, test);
	    }
//	    public static synchronized ExtentTest getTest() {
//	        return test;
//	    }
	    public static synchronized ExtentTest getTest() {
	    	long getCurrentThread=Thread.currentThread().getId();
	        test= get(getCurrentThread);
	         return test;
	    }
	    public static synchronized void step(String log) {
	        ExtentTest test = getTest();
	        test.log(Status.INFO, MarkupHelper.createLabel("Performing -> " + log, ExtentColor.INDIGO));
	    }
}