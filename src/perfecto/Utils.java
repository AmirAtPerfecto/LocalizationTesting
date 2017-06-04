package perfecto;


import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import LocalizationTestingPackage.ProxyProvider;
import LocalizationTestingPackage.ProxyProvider.ProxyLocation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.chrome.ChromeOptions;

public class Utils {

	// Appium
		public static RemoteWebDriver getRemoteWebDriver(String platformName, String platformVersion, String manufacturer,
				String model, String appType, String appID) throws IOException {
	        System.out.println("Run started");

	        String browserName = "mobileOS";
	        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		    
			// Set cloud host and credentials values from CI, else use local values
			String PERFECTO_HOST = System.getProperty("np.testHost", System.getenv().get("PERFECTO_CLOUD"));
			String PERFECTO_USER = System.getProperty("np.testUsername", System.getenv().get("PERFECTO_CLOUD_USERNAME"));
			String PERFECTO_SECURITY_TOKEN = System.getProperty("np.testPassword", System.getenv().get("PERFECTO_CLOUD_SECURITY_TOKEN"));
			
			capabilities.setCapability("user", PERFECTO_USER);
			capabilities.setCapability("securityToken", PERFECTO_SECURITY_TOKEN);
	        capabilities.setCapability("platformName", platformName);
	        capabilities.setCapability("platformVersion", platformVersion);
	        capabilities.setCapability("manufacturer", manufacturer);
	        capabilities.setCapability("model", model);
	        if (null != appType)
	        	capabilities.setCapability(appType, appID); //app id
//	        capabilities.setCapability("winAppId", "Microsoft.MicrosoftEdge_8wekyb3d8bbwe!App"); //app id

	        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
	        capabilities.setCapability("automationName", "Appium");

	        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
	        PerfectoLabUtils.setExecutionIdCapability(capabilities, PERFECTO_HOST);

	        // Application settings examples.
	        // capabilities.setCapability("app", "PRIVATE:applications/Errands.ipa");
	        // For Android:
	        // capabilities.setCapability("appPackage", "com.google.android.keep");
	        // capabilities.setCapability("appActivity", ".activities.BrowseActivity");
	        // For iOS:
	        // capabilities.setCapability("bundleId", "com.yoctoville.errands");

	        // Add a persona to your script (see https://community.perfectomobile.com/posts/1048047-available-personas)
	        //capabilities.setCapability(main.WindTunnelUtils.WIND_TUNNEL_PERSONA_CAPABILITY, main.WindTunnelUtils.GEORGIA);

	        // Name your script
	        // capabilities.setCapability("scriptName", "AppiumTest");

	        //AndroidDriver driver = new AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
//	         IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
	        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
	        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

			return driver;

		}

		
// Selenium
	public static RemoteWebDriver getRemoteWebDriver(String platformName, String platformVersion, String browserName,
			String browserVersion, String screenResolution, ProxyLocation location) throws MalformedURLException {
	    
		// Set cloud host and credentials values from CI, else use local values
		String PERFECTO_HOST = System.getProperty("np.testHost", System.getenv().get("PERFECTO_CLOUD"));
		String PERFECTO_USER = System.getProperty("np.testUsername", System.getenv().get("PERFECTO_CLOUD_USERNAME"));
		String PERFECTO_SECURITY_TOKEN = System.getProperty("np.testPassword", System.getenv().get("PERFECTO_CLOUD_SECURITY_TOKEN"));
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", PERFECTO_USER);
		capabilities.setCapability("securityToken", PERFECTO_SECURITY_TOKEN);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("browserVersion", browserVersion);
		
		ProxyProvider.setProxyCapabilities(capabilities, location);
		if (!screenResolution.isEmpty()) {
			capabilities.setCapability("resolution", screenResolution);
			System.out.println("Creating Remote WebDriver on: " + platformName + " " + platformVersion + ", " + browserName + " " + browserVersion + ", " + screenResolution);
		}
		else {
			if (!platformName.isEmpty())
				System.out.println("Creating Remote WebDriver on: " + platformName + " " + platformVersion);
			else
				System.out.println("Creating Remote WebDriver on: " + browserName);
		}

		RemoteWebDriver webdriver = new RemoteWebDriver(
				new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);

		// Define RemoteWebDriver timeouts
		webdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//webdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		// Maximize browser window on Desktop
		if (!screenResolution.isEmpty()) {
		//	webdriver.manage().window().maximize();
		}

		return webdriver;
	}

}
