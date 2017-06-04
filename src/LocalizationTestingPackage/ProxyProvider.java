package LocalizationTestingPackage;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class ProxyProvider {
	public static enum ProxyLocation {NONE, US, BRAZIL, ARGENTINA, INDONESIA, 
		RUSSIA, SOUTH_AFRICA, JAPAN, FRANCE, ITALY,MEXICO, BRAZIL_CELLULAR_OI, BRAZIL_CELLULAR_WIND};

		public static void setProxyCapabilities(DesiredCapabilities capabilities, ProxyLocation location){
			if (ProxyLocation.NONE == location || null == location) return;
			String httpProxy = getProxyString(location);
			Proxy proxy = new Proxy();
			proxy.setProxyType(ProxyType.MANUAL);
			proxy.setHttpProxy(httpProxy);
			//		proxy.setSslProxy(sslProxy);
			//		proxy.setFtpProxy(ftpProxy);

			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		}
		private static String getProxyString(ProxyLocation location){
			if (null == location) return "";
			switch (location){
			// from https://www.proxynova.com/proxy-server-list/country-us/
			case BRAZIL: return "191.7.210.30:3128";
			case US: return "71.199.12.18:8080";
			case ARGENTINA: return "181.47.53.129:3128";
			case INDONESIA: return "119.252.172.133:3128";
			case RUSSIA: return "46.16.226.10:8080";
			case SOUTH_AFRICA: return "";
			case JAPAN: return "218.46.23.23:8080";
			case FRANCE: return "37.187.119.226:3128";
			case ITALY: return "37.159.208.218:3128";
			case MEXICO: return "200.76.251.166:3128";
			//		case "brazil cellular oi": return ProxyLocation.BRAZIL_CELLULAR_OI;
			//		case "brazil cellular wind": return ProxyLocation.BRAZIL_CELLULAR_WIND;
			default: 
				return "";
			}

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
