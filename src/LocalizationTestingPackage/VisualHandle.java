package LocalizationTestingPackage;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

public class VisualHandle {

	public static void enterCredentials(RemoteWebDriver driver,String username, String password)
	{

		System.out.println("\nSet Credentials method\n");

		try

		{
			setText(driver, "User name", username, "20", "Left", "10%");
			setText(driver, "Private", password, "20", "Above", "8%");
			Thread.sleep(10000);
		}

		catch (Exception ex)
		{

			System.out.println("\nUnable to execute Set Text method\n" +ex);
		}

	}



	public static void setText(RemoteWebDriver driver, String textToSelect, String textToEnter, String timeout, String touchPositionDirection, String touchPositionOffsetValue)
	{

		// textToSelect=The anchor label
		// testToEnter = Text to enter to field
		// touchPositionDirection = Where is the anchor label in relation to the text box
		// touchPositionOffsetValue= how far (%) is the anchor label from the text field
		// read more: http://developers.perfectomobile.com/pages/viewpage.action?pageId=14877169
		System.out.println("\nSet Text method\n");

		try

		{

			String command = "mobile:edit-text:set";
			Map<String, Object> params = new HashMap<>();
			params.put("label", textToSelect);
			params.put("text", textToEnter);
			params.put("timeout", timeout);
			params.put("label.direction", touchPositionDirection);
			params.put("label.offset", touchPositionOffsetValue);

			driver.executeScript(command, params);

			System.out.println("\nSet Text Passed.\n");	       
		}
		catch (Exception ex)
		{	           
			System.out.println("\nUnable to execute Set Text method\n" +ex);
		}
	}

}