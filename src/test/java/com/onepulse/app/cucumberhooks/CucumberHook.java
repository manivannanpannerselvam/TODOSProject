package com.onepulse.app.cucumberhooks;



import com.onepulse.app.screens.TestBasePage;
import com.onepulse.app.stepdefinitions.mobilesteps.BaseSteps;
//import com.product.genericcomponents.appium.AppiumCommands;
//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.exception.TapException;
//import com.product.genericcomponents.exception.TapExceptionType;
//import com.product.genericcomponents.reporting.TapReporting;
import com.prod.tap.appium.AppiumCommands;
import com.prod.tap.config.Configvariable;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.reporting.TapReporting;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

@ComponentScan(basePackages = {"com.onepulse.app", "com.prod.tap"})
@Configuration
public class CucumberHook {
    private static final Logger logger = Logger.getLogger(CucumberHook.class);
    private static boolean isReporterRunning = false;
    public static ConfigurableApplicationContext context;
    private AppiumCommands appiumCommands;

    private Configvariable configvariable;

    private TapReporting tapReporting = new TapReporting();

   // private ScenarioUtils scenarioUtils = (ScenarioUtils) TapBeansLoad.getBean(ScenarioUtils.class);




    @Before
    public void checkReporterRunning(Scenario scenario) {
     //   scenarioUtils.setCurrentScenario(scenario);
        if (!isReporterRunning) {
            tapReporting.customExtentReport(System.getProperty("user.dir") + "/reports/cucumber/TestReports/TestReport.html");
            isReporterRunning = true;
        }
    }
    @Before
    public void initializeFramework() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(CucumberHook.class);
            configvariable = context.getBean(Configvariable.class);
//            configvariable.setupEnvironmentProperties(System.getProperty("pulse.env"));
        }
        appiumCommands = context.getBean(AppiumCommands.class);
    }



    @After
    public void afterTest(Scenario scenario) {
        String screenshotFile = System.getProperty("user.dir") + "/reports/cucumber/TestReports/" + scenario.getName().replaceAll(" ", "_") + ".jpeg";
      //  if (scenario.isFailed()) {
            takeScreenshotOfFailedTestCases(screenshotFile);
            embedScenarioScreenshot(scenario);
      //  }

//        tapReporting.createTest(scenario, screenshotFile);
//        tapReporting.writeToReport();
    }


    @After
    public void baseState() {

    }
    public void takeScreenshotOfFailedTestCases(String screenShotFilePath) {
        WebDriver driver = appiumCommands.getDriver();
        if (driver != null) {
            try {
                final File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenShotFilePath));
            } catch (Exception ex) {
                logger.info("Failed to take screenshot of ");
                throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to take screenshot of ");
            }
        } else {
            logger.info("Driver is null, can't take screenshot");
        }

    }

    public void embedScenarioScreenshot(Scenario scenario) {
        WebDriver driver = TestBasePage.driver;
        if (driver != null) {
            try {
                byte[] screenshot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                //create InputStream for ImageIO using png byte[]
                ByteArrayInputStream bais = new ByteArrayInputStream(screenshot1);
                //read png bytes as an image
                BufferedImage bufferedImage = ImageIO.read(bais);
                BufferedImage newbytes = ImageIO.read(bais);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // Using Thumbnailator to reduce the dimensions of the screenshot
                Thumbnails.of(bufferedImage).size(300, 400).outputFormat("PNG").toOutputStream(baos);
                scenario.embed(baos.toByteArray(), "image/png");
            } catch (Exception ex) {
                logger.info("Failed to take screenshot of ");
                throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to take screenshot of ");
            }
        } else {
            logger.info("Driver is null, can't take screenshot");
        }
    }


}