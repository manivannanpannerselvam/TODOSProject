package com.onepulse.app.utils;

//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.driver.TapDriver;
import com.prod.tap.config.Configvariable;
import com.prod.tap.driver.TapDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */
@Component
public class CreateSessionCucumber {

    private final Logger LOGGER = Logger.getLogger(CreateSessionCucumber.class.getName());

    public WebDriver driver = null ;
    private String OS;

    private static String IOS_UDID = "00008101-001E31410242001E";
    private static String IOS_PLATFORM = "14.4.1";

    private static String APP_LANGUAGE = System.getProperty("pulse.language");
    private static String APP_COUNTRY = System.getProperty("pulse.country");
//    private static String PROXY_USER = System.getProperty("proxy.user");
//    private static String PROXY_PASS = System.getProperty("proxy.pass");
//    private static String PROXY_URL = "http://10.163.39.77:8080";


    @Autowired
    public TapDriver tapDriver;

    @Autowired
    private Configvariable configvariable;



    /**
     * this method creates the driver depending upon the passed parameter (android or iOS)
     * and loads the properties files (config and test data properties files).
     *
     * @param platform android or iOS
     * @throws Exception issue while loading properties files or creation of driver.
     */
    public void createDriver(String platform, String appPath) throws Exception {
        LOGGER.info("--------------------------------------");
        LOGGER.info("Creating driver for " + platform);
    //    tapDriver.capabilities.setCapability("autoDismissAlerts", true);
        if (platform.equalsIgnoreCase("android")) {

            TapDriver.NO_RESET = true;



            tapDriver.androidDriver(appPath, Configvariable.envPropertyMap.get("pulse.android.app.package"), Configvariable.envPropertyMap.get("pulse.android.app.activity"));
            LOGGER.info("Android driver created");
            driver = tapDriver.getWebDriver();

        } else if (platform.equalsIgnoreCase("iOS")) {
         //   tapDriver.capabilities.setCapability("newCommandTimeout", 10000);
           // tapDriver.capabilities.setCapability("autoDismissAlerts",false);
            tapDriver.iOSDriver(appPath, Configvariable.envPropertyMap.get("pulse.ios.app.bundle.id"), IOS_UDID, IOS_PLATFORM);
            LOGGER.info("iOS device UDID " + System.getenv("DEVICEFARM_DEVICE_UDID_FOR_APPIUM"));
            LOGGER.info("iOS device OS version " + System.getenv("DEVICEFARM_DEVICE_OS_VERSION"));
            //    tapDriver.iOSDriver(appPath, Configvariable.envPropertyMap.get("pulse.ios.app.bundle.id"), System.getenv("DEVICEFARM_DEVICE_UDID_FOR_APPIUM"), System.getenv("DEVICEFARM_DEVICE_OS_VERSION"));
            LOGGER.info("iOS driver created");
        }
        //else if(platform.equalsIgnoreCase(System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME")))
        else if(platform.equalsIgnoreCase("web"))
        {
          //  WebDriverManager.chromedriver().proxyUser(PROXY_USER).proxyPass(PROXY_PASS).proxy(PROXY_URL).setup();
           driver = tapDriver.createChromeDriver();
        }
        else {

            driver = tapDriver.getWebDriver();
        }
      /*  if(TestBasePage.platform.equalsIgnoreCase("android")) {
            // ((AndroidDriver)driver).setLocation(new Location(22.3, 114.2, 40)); // for Kaulalampur
            ((AndroidDriver) driver).setLocation(new Location(14.6, 121, 18));
        }
            Thread.sleep(100000);
            ((AndroidDriver)driver).closeApp();
            Thread.sleep(5000);
            ((AndroidDriver)driver).launchApp(); */
    }

    /**
     * this method quit the driver after the execution of test(s)
     */
    //@AfterMethod
    public void teardown() {
        LOGGER.info("Shutting down driver");
     //   driver.quit();
    }

    public WebDriver getWebDriver() {
        return this.driver;
    }

}