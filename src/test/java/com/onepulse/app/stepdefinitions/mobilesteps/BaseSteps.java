package com.onepulse.app.stepdefinitions.mobilesteps;

import com.onepulse.app.config.ConfigDetails;
import com.onepulse.app.cucumberhooks.CucumberHook;
import com.onepulse.app.screens.TestBasePage;
import com.onepulse.app.utils.CreateSessionCucumber;
//import com.product.genericcomponents.appium.AppiumCommands;
//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.driver.TapDriver;
import com.prod.tap.appium.AppiumCommands;
import com.prod.tap.config.Configvariable;
import com.prod.tap.driver.TapDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class BaseSteps {
    public static WebDriver driver;
    public static String platform;

    private CreateSessionCucumber createSessionCucumber = CucumberHook.context.getBean(CreateSessionCucumber.class);

    private AppiumCommands appiumCommands = CucumberHook.context.getBean(AppiumCommands.class);


    private TestBasePage testBasePage = CucumberHook.context.getBean(TestBasePage.class);

    private Configvariable configvariable = CucumberHook.context.getBean(Configvariable.class);

    private TapDriver tabdriver = CucumberHook.context.getBean(TapDriver.class);


    @Given("^User has pulse app$")
    public void userHasPulseAppGeneric() throws Exception {

        //testBasePage.mockDeviceLocation();

        testBasePage.launchApp();
    }





    @Given("^Launch HR portal$")
    public void launchHRPortal() {

    }




    @Then("I wait for (.*) sec")
    public void waitTime(int time) throws InterruptedException {
        testBasePage.waitTime(time);
    }

    @Given("^I scroll down$")
    public void scrollDown() {
        testBasePage.scrollPageDown();
    }

    @Then("^I take a photo from camera$")
    public void takePhotoFromCamera() throws InterruptedException {
        testBasePage.takePictureFromCamera();
    }

    @Then("^I give camera permission$")
    public void giveCameraPermission() {
        testBasePage.allowCameraPermission(TestBasePage.platform);
    }


    @Then("^I re take the photo from camera$")
    public void reTakeCameraPhoto() throws InterruptedException {
        testBasePage.takePictureFromCamera();
    }

    @Given("^I scroll up$")
    public void scrollUp() throws InterruptedException {
        testBasePage.scrollPageUP();
    }

    @When("^I scroll up till \"([^\"]*)\" element displayed$")
    public void scrollTillElementDisplayed(String claimType) {
        testBasePage.scrollUpTillElementDisplayed(configvariable.expandValue(claimType));
    }

    @Given("^I start the appium server$")
    public void iStartTheAppiumServer() throws IOException, InterruptedException {
        tabdriver.startAppiumServer(System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME"));
    }

    @Given("^I hide one pulse app mobile keyboard$")
    public void hideKeyboard() {
        if (TestBasePage.platform.equalsIgnoreCase("ios")) {
            appiumCommands.pressIOSKeyBoardKey("Return");
        } else {
          appiumCommands.hideKeyboard();
        }
    }


    @Then("^I get HR Portal parent window handle$")
    public void getParentWindowHandle() {
        String windowsHandle=this.testBasePage.getParentWindowHandle();

        System.out.println("Parent windows Handle  "+windowsHandle);

    }
}
