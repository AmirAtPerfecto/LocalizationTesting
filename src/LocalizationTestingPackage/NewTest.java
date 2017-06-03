package LocalizationTestingPackage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;

import LocalizationTestingPackage.Utils.ProxyLocation;

public class NewTest {
	RemoteWebDriver driver;
	ReportiumClient reportiumClient;

	@Test 
	public void testLocalization() throws InterruptedException {

	        reportiumClient.testStart("ProxyTest", new TestContext("Proxy", "tag3"));
	        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	        driver.get("https://www.google.fr/#q=amazon");
			//VisualHandle.enterCredentials(driver, System.getenv().get("PERFECTO_PROXY_USERNAME"), System.getenv().get("PERFECTO_PROXY_PASSWORD"));
	        //PerfectoUtils.ocrTextCheck(driver, "Log in", 99, 20);
			driver.executeScript("window.location.href='http://www.whatismyipaddress.com'");
			
			Thread.sleep(10000);
			PerfectoUtils.screenshot(driver);
			driver.get("http://www.amazon.fr");
			Thread.sleep(5000);
			PerfectoUtils.screenshot(driver);
			driver.get("http://www.mastercard.com");
			PerfectoUtils.screenshot(driver);
			Thread.sleep(5000);
	}
		// Create Remote WebDriver based on testng.xml configuration
	@Parameters({ "platformName", "platformVersion", "browserName", "browserVersion", "screenResolution",  "proxyLocation" })
	@BeforeTest
	public void beforeTest(String platformName, String platformVersion, String browserName, String browserVersion, String screenResolution, String proxyLocation) throws IOException {
		ProxyLocation pLocation= Utils.mapTestNGLocationToProxyLocation(proxyLocation);
		driver = Utils.getRemoteWebDriver(platformName, platformVersion, browserName, browserVersion, screenResolution, pLocation);        
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("My Project", "1.0"))
				.withJob(new Job("My Job", 45))
				.withContextTags("tag1")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	} 


	
	@AfterTest
	public void afterTest() throws IOException {
		String reportURL = reportiumClient.getReportUrl();
		System.out.println("########### ========>>>>>>>> Report URL: "+ reportURL); 
		
		// Abort test in case of an unexpected error.
		driver.close();
		driver.quit();	

	}	

}
