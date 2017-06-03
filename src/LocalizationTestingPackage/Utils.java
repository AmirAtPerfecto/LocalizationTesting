package LocalizationTestingPackage;


import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.chrome.ChromeOptions;

public class Utils {
	public static enum ProxyLocation {NONE, US, BRAZIL, ARGENTINA, INDONESIA, 
		RUSSIA, SOUTH_AFRICA, JAPAN, FRANCE, ITALY,MEXICO, BRAZIL_CELLULAR_OI, BRAZIL_CELLULAR_WIND};

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
		
		setProxyCapabilities(capabilities, location);
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
	private static void setProxyCapabilities(DesiredCapabilities capabilities, ProxyLocation location){
			if (ProxyLocation.NONE == location || null == location) return;
			String httpProxy = "37.187.119.226:3128";//"200.229.202.72:8080";//"181.41.197.56:443";//"70.25.66.185:80";//"200.229.202.72:8080";//"70.25.66.185:80";//
			Proxy proxy = new Proxy();
			proxy.setProxyType(ProxyType.MANUAL);
			proxy.setHttpProxy(httpProxy);
			//		proxy.setSslProxy(sslProxy);
			//		proxy.setFtpProxy(ftpProxy);
/*			capabilities.setCapability("java.net.socks.username", "perfectomobile");
			capabilities.setCapability("java.net.socks.password", "pmPass45");
			capabilities.setCapability("socksUsername", "perfectomobile");
			capabilities.setCapability("socksPassword", "pmPass45");
*/			

			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	}
	public static ProxyLocation  mapTestNGLocationToProxyLocation(String testNGLocation ){
		if (null == testNGLocation) return ProxyLocation.NONE;
		switch (testNGLocation.toLowerCase()){
		case "brazil": return ProxyLocation.BRAZIL;
		case "US": return ProxyLocation.US;
		case "argentina": return ProxyLocation.ARGENTINA;
		case "indonesia": return ProxyLocation.INDONESIA;
		case "russia": return ProxyLocation.RUSSIA;
		case "south africa": return ProxyLocation.SOUTH_AFRICA;
		case "japan": return ProxyLocation.JAPAN;
		case "france": return ProxyLocation.FRANCE;
		case "italy": return ProxyLocation.ITALY;
		case "mexico": return ProxyLocation.MEXICO;
		case "brazil cellular oi": return ProxyLocation.BRAZIL_CELLULAR_OI;
		case "brazil cellular wind": return ProxyLocation.BRAZIL_CELLULAR_WIND;
		default: 
			return ProxyLocation.NONE;
		}
	}

}
