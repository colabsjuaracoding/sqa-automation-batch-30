package id.co.juaracoding.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentHtmlReporter html = new ExtentHtmlReporter("target/extent-report.html");
            html.config().setDocumentTitle("SQA Automation Report");
            html.config().setReportName("Web & API Tests");
            html.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(html);
        }
        return extent;
    }
}
