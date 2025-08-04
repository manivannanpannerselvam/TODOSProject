package com.onepulse.app.screens;

import com.onepulse.app.utils.CreateSessionCucumber;
//import com.product.genericcomponents.appium.AppiumCommands;
//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.cucumberUtils.ScenarioUtils;
//import com.product.genericcomponents.driver.TapDriver;
//import com.product.genericcomponents.exception.TapException;
//import com.product.genericcomponents.exception.TapExceptionType;
//import com.product.genericcomponents.filehandling.CsvUtils;
//import com.product.genericcomponents.selenium.*;
import com.prod.tap.appium.AppiumCommands;
import com.prod.tap.config.Configvariable;
import com.prod.tap.cucumberUtils.ScenarioUtils;
import com.prod.tap.driver.TapDriver;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.filehandling.CsvUtils;
import com.prod.tap.filehandling.FileReaderUtil;
import com.prod.tap.selenium.*;
import de.redsix.pdfcompare.CompareResult;
import de.redsix.pdfcompare.CompareResultWithPageOverflow;
import de.redsix.pdfcompare.PdfComparator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TestBasePage {
    private final static Logger LOGGER = Logger.getLogger(TestBasePage.class);


    public static WebDriver driver;

    public static String platform;

    public static String language;

    private static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    private CsvUtils csvUtils = new CsvUtils();


    @Autowired
    private SeleniumBase seleniumBase;

    @Autowired
    private ExecuteCommand executeCommand;

    @Autowired
    private Configvariable configvariable;

    @Autowired
    private TapDriver tapDriver;

    @Autowired
    private JavaScriptExec javaScriptExec;

    @Autowired
    private WebTableMethods webTableMethods;

    @Autowired
    private CreateSessionCucumber createSessionCucumber;

    @Autowired
    private AppiumCommands appiumCommands;

    @Autowired
    private ScenarioUtils scenarioUtils;

    @Autowired
    private ExecutionContext executionContext;


//    @Autowired
//     public Commands commands;


    MobileElement element;

    AppiumDriver appiumDriver;

    TouchAction touchAction;


    public String Popuptexts = "${pulsepopup.text}";
    public String popupstart = "${pulsepopupstart.text}";

    private WebDriverWait wait;

    private static String pickers = "${datepickerwheel}";
    public static String datePickerEditField = "${android.datepicker.editfield}";
    public static String dayselectorUpButton = "${android.datepicker.UpArrowButton}";
    public TestBasePage testBasePage;

    public void launchApp() {
      //  if ((System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME") != null)
        //        && (System.getProperty("app.language") != null)) {

         //   platform = System.getenv("DEVICEFARM_DEVICE_PLATFORM_NAME");
            language = System.getProperty("app.language");
     //   } else {
       //     platform = System.getProperty("osType");
         //   language = System.getProperty("app.language");
      //  }
        try {
            createSessionCucumber.createDriver(platform, "");
            driver = createSessionCucumber.getWebDriver();

            appiumCommands.setDriver(driver);
//            if (!language.equalsIgnoreCase("en"))
//                switchLanguage(language);
        } catch (Exception e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Driver not create for [{}], [{}]", platform, e.getMessage());
        }


    }

    public boolean isChildWindowPresent() {
        Set<String> childWindows = driver.getWindowHandles();
        Iterator i1 = childWindows.iterator();
        if(childWindows.size() > 1){
            while (i1.hasNext()) {
                String childWindow = (String) i1.next();
                if(!configvariable.getStringVar("parent.window.handle").equalsIgnoreCase(childWindow)){
                    executeCommand.executeCommand(driver, Commands.SWITCH_TO_WINDOW_HANDLE, null, childWindow, "");
                //    executeCommand.executeCommand(driver, Commands.CLOSE_BROWSER, null, "", "");
                }

            }
            return true;
        }else{
            return false;
        }
    }


    public void switchToParentWindow() {
        String parentWindow = configvariable.getStringVar("parent.window.handle");
        executeCommand.executeCommand(driver, Commands.SWITCH_TO_WINDOW_HANDLE, null, parentWindow, "");
    }


    public void navigateToUrl(String url) {
        //     WebDriver driver = testBasePage.getDriver();
        navigateToApplicationUrl(configvariable.expandValue(url));
    }

    public void launchApplication(String url) {
        driver = this.executeCommand.getDriverForRequiredBrowser(this.executionContext.getBrowserType());
        this.executeCommand.executeCommand(driver, Commands.OPEN_URL, (Robot)null, url, "");
    }

    public void launchApp(String appURL) {
        platform = System.getProperty("platform.type");
        scenarioUtils.write(appURL);
        System.out.println(appURL);
       String gg= System.getProperty("system.proxy.apply");
       System.out.println("PROXY IS ------------------> "+gg);
        //  if ("desktop".equalsIgnoreCase(platform)) {
            try {
                launchApplication(appURL);
                driver = SeleniumBase.driver;
//                if (executionContext.isCookieRequired()) {
//                    seleniumBase.setBrowserCookie();
//                    waitTime(15);
                    driver.navigate().refresh();
              //  Thread.sleep(9000);
           //    }

            } catch (Exception e) {
                driver = SeleniumBase.driver;
//                if (executionContext.isCookieRequired()) {
//                    seleniumBase.setBrowserCookie();
//                    waitTime(10);
//                    driver.navigate().refresh();
//                }

                LOGGER.info("error in cookie");
            }

      //  }

    }

    public void navigateToApplicationUrl(String appUrl) {
        platform = System.getProperty("platform.type");
        //   configvariable.setStringVariable("chrome", "web.browser.type");
     //   if ("desktop".equalsIgnoreCase(platform)) {
        System.out.println(appUrl);
            driver.get(appUrl);
      //  }
        // configvariable.setStringVariable(System.getProperty("web.browser.type"), "web.browser.type");

    }
    public void clearText(String locator) {
        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}));
        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, Keys.chord(new CharSequence[]{Keys.BACK_SPACE}));
        this.executeCommand.executeCommand(this.driver, Commands.CLEAR, (Robot)null, locator, "");
    }

    public boolean isElementPresent(String locator) {
        return this.executeCommand.executeCommand(this.driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, (Robot)null, locator, "10");
    }

    public boolean isElementSelected(String locator) {
        return this.executeCommand.executeCommand(this.driver, Commands.IS_ELEMENT_SELECTED, (Robot)null, locator, "");
    }

//    public int getTableRowsCount(String locator) {
//        return this.webTableMethods.getRowsCountInTable(this.driver, locator);
//    }
    public List<String> getColumnDataForTdHeaders(String locator, String columnName) {
        return this.webTableMethods.getTableColumnDataForTDHeaders(this.driver, locator, columnName);
    }

    public List<String> getWebTableHeaderList(String locator) {
        return this.webTableMethods.getTableHeaderNameForTypeTD(this.driver, locator);
    }

    public boolean isFilePresentInFolder(String filePath) {
        for(int count = 10; !FileReaderUtil.isFileExist(filePath) && count > 0; --count) {
            this.waitTime(2);
        }

        return FileReaderUtil.isFileExist(filePath);
    }

    public int getRowsCountInTable(WebDriver driver, String locator) {
        List rows = driver.findElements(By.tagName(locator));
     //   logger.info("No of rows are : " + rows.size());
        System.out.println("What is size " +rows.size());
        return rows.size();
    }



    public int getTableRowsCounts(String locator) {
        return getRowsCountInTable(driver, locator);
    }

    public List<WebElement> getWebElements(By locator) {
        return driver.findElements(locator);
    }

    public List<String> getAllDropDownValueWithoutSelectClass(String locator) {
        List<String> actualDropDownListList = new ArrayList();
        List<WebElement> dropDownElements = getWebElements(By.tagName(locator));
        for (WebElement value : dropDownElements) {
            try {
                actualDropDownListList.add(value.getText());
            }
            catch(Exception ie)
            {
                System.out.println("Exception Occur");
            }
        }
        for(int i=0;i<dropDownElements.size();i++)
        {
            try {
                System.out.println(dropDownElements.get(i).getText());
            }
            catch(Exception ie)
            {
                System.out.println(ie);
            }
        }
        return actualDropDownListList;
    }

    public void mouseHoverAndClickOnElement(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "10");
        executeCommand.executeCommand(driver, Commands.MOUSE_HOVER_AND_CLICK, null, locator, "");
    }
    public int getTableRowsCount(String locator) {
        return webTableMethods.getRowsCountInTable(driver, locator);
    }

    public String getTableCellData(String locator, int rowCount, int columnCount) {
        return this.webTableMethods.getTDCellValue(this.driver, locator, rowCount, columnCount);
    }

    public Map<String, Integer> getColumnIndexOfTableHeader(String locator) {
        return this.webTableMethods.getTableColumnIndexTypeTD(this.driver, locator);
    }


    public void uploadFileUsingFileInputField(String locator, String filePath) {
        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, filePath);
    }

    public void scrollPage() {
        JavascriptExecutor jse = ((JavascriptExecutor)this.driver);
        jse.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }


    public void scrollTillElementFound(String locator) {
        this.javaScriptExec.scrollWebPageTillElementVisible(TestBasePage.driver, locator);
    }

    public void scrollPageUP_web() {
        JavascriptExecutor jse = (JavascriptExecutor) TestBasePage.driver;
        jse.executeScript("window.scrollTo(0, -document.body.scrollHeight)", new Object[0]);
    }

    public void setTextWithoutTab(String locator, String value) {
        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}));

        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, Keys.chord(new CharSequence[]{Keys.BACK_SPACE}));
        this.executeCommand.executeCommand(this.driver, Commands.CLEAR, (Robot)null, locator, "");
        this.executeCommand.executeCommand(this.driver, Commands.SEND_KEYS, (Robot)null, locator, value);
        if (!this.getAttributeValue(locator, "value").equalsIgnoreCase(value)) {
            this.enterTextCharByChar(locator, value);
        }

    }

    public String getParentWindowHandle() {
        executeCommand.executeCommand(driver, Commands.GET_PARENT_WINDOW_HANDLE, null, "", "");
        return configvariable.getStringVar("parent.window.handle");
    }



    public boolean pdfImageCompareAndWriteDifferenceInPDF(String expectedFile, String actualFile, String exceptionPath) {
        File file1InputStream = new File(expectedFile);
        File file2InputStream = new File(actualFile);

        try {
            CompareResult result = (new PdfComparator(file1InputStream, file2InputStream, new CompareResultWithPageOverflow())).compare();
            if (result.isNotEqual()) {
                result.writeTo(exceptionPath);
                return false;
            } else {
                return true;
            }
        } catch (IOException var7) {
           // logger.error("IO Exception while processing comparePdfFilesByImage", var7);
            throw new TapException(TapExceptionType.IO_ERROR, "IO Exception while processing comparePdfFilesByImage [{}]", new Object[]{var7.getMessage()});
        }
    }

    public void enterTextCharByChar(String locator, String value) {
        WebElement element = this.driver.findElement(By.xpath(locator));

        for(int i = 0; i < value.length(); ++i) {
            element.sendKeys(new CharSequence[]{Character.toString(value.charAt(i))});
            String val = element.getAttribute("value");
            if (!val.equals(value.substring(0, i + 1))) {
                element.sendKeys(new CharSequence[]{Character.toString(value.charAt(i))});
            }
        }

    }

    public String getAttributeValue(String locator, String attributeName) {
        this.executeCommand.executeCommand(this.driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, (Robot)null, locator, "10");
        this.executeCommand.executeCommand(this.driver, Commands.GET_ATTRIBUTE, (Robot)null, locator, attributeName);
        return this.configvariable.getStringVar("element.attribute");
    }



    public void relaunchApp() {
        //  ((AppiumDriver)driver).closeApp();
        ((AppiumDriver) driver).launchApp();
        ((AppiumDriver) driver).activateApp(Configvariable.envPropertyMap.get("pulse.android.app.package"));
    }

    public void deleteOTP() {
        if (platform.equalsIgnoreCase("android")) {
            ((AndroidDriver) driver).pressKeyCode(67);
        } else {

        }
    }

public boolean getURL(String url)
{
    driver.navigate().to(url);
    return true;
}


    public void hideIOSKeyBoard(String keyValue) {
       // ((IOSDriver) driver).hideKeyboard(HideKeyboardStrategy.PRESS_KEY, keyValue); // Done can change to whatever valid on our screen
        ((IOSDriver) driver).hideKeyboard(HideKeyboardStrategy.TAP_OUTSIDE);
    }

    public boolean isEnabled(String locator) {
        executeCommand.executeCommand(driver, Commands.IS_ELEMENT_ENABLED, null, locator, "5");
        return executeCommand.executeCommand(driver, Commands.IS_ELEMENT_ENABLED, null, locator, "4");
    }

    public void setText(String locator, String value) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "5");
        executeCommand.executeCommand(driver, Commands.CLEAR, null, locator, "");
        executeCommand.executeCommand(driver, Commands.SEND_KEYS_TAB, null, locator, value);
    }


        public boolean executeCommand(WebDriver driver, Robot robot, String locator, String value) {
           // driver.findElement(By.xpath(locator)).click();
            driver.findElement(new ByAll(By.xpath(locator),By.id(locator),By.cssSelector(locator))).click();
            return true;
        }

    public void clickButton(String locator) {
       // executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "5");
      //  executeCommand.executeCommand(driver, Commands.CLICK, null, locator, "");
        executeCommand(driver,  null, locator, "");
    }


    
    public void verifyTextEachWebElement(List<By> byList) {
        Iterator var2 = byList.iterator();

        By reportStaticOne;
        boolean flag;
        do {
            if (!var2.hasNext()) {
                return;
            }

            reportStaticOne = (By)var2.next();
            flag = false;
            flag = appiumCommands.isElementDisplayed(reportStaticOne);
        } while(flag);

        LOGGER.error("Not found the text: " + reportStaticOne.toString());
        throw new TapException(TapExceptionType.EXPECTED_WEBELEMENT_DOESNOT_EXIST, "Not found the text:{}", new Object[]{reportStaticOne.toString()});
    }

    public String getText(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "5");
        executeCommand.executeCommand(driver, Commands.GET_TEXT, null, locator, "");
        return configvariable.getStringVar("element.text");
    }

    public boolean isElementDisplayed(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "5");
        return executeCommand.executeCommand(driver, Commands.IS_ELEMENT_DISPLAYED, null, locator, "");

    }

    public boolean Switch_to_frame(String locator) {
        return executeCommand.executeCommand(driver,Commands.SWITCH_TO_FRAME,  null , locator, "");
    }

    public void clickButtonUsingJs(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "5");
        javaScriptExec.clickElementJS(driver, locator);
    }

    public void scrollPageDown() {

        appiumCommands.swipe(AppiumCommands.DIRECTION.DOWN, 6000);
    }

    public void scrollPageDownForEnvironments() {

        appiumCommands.swipe(AppiumCommands.DIRECTION.DOWN, 5000);
    }


    public Map<String, Map<String, String>> getTableAllData(String locator, String columnName) {
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        Map<String, Integer> columns = webTableMethods.getTableColumnIndex(driver, locator);
        int rows = webTableMethods.getRowsCountInTable(driver, locator);
        for (int rowCount = 1; rowCount <= rows; rowCount++) {
            Map<String, String> rowData = new HashMap<>();
            for (String col : columns.keySet()) {
                String value = "";
                if (col.contains("Plan") && !col.contains("Plan Type")) {
                    try {
                        value = webTableMethods.getTDCellValue(driver, locator, rowCount, columns.get(col)).replace("S$", "").replaceAll(",", "");
                    } catch (Exception e) {
                        value = "";
                    }

                    rowData.put(col.replace("\ninformation", ""), value.replace("\ninformation", "").replaceAll("\n", "").trim());
                }
            }
            dataMap.put(webTableMethods.getTDCellValue(driver, locator, rowCount, columns.get(columnName)).replace("\ninformation", "").replaceAll("\n", " "), rowData);
        }
        return dataMap;
    }

    public List<String> getColumnData(String locator, String columnName) {
        List<String> arrList = new ArrayList<String>();
        Map<String, Integer> columns = webTableMethods.getTableColumnIndex(driver, locator);
        int rows = webTableMethods.getRowsCountInTable(driver, locator);
        int column = columns.get(columnName);
        String value = "";
        for (int rowCount = 1; rowCount <= rows; rowCount++) {
            try {
                value = webTableMethods.getTDCellValue(driver, locator, rowCount, column).replace("\ninformation", "").replaceAll("\n", "").trim();
            } catch (Exception e) {
                value = "";
            }
            arrList.add(value);
        }
        return arrList;
    }

    public String getElementText(String locator) {
        WebElement cell = driver.findElement(By.xpath(locator));
        return cell.getText();
    }

    public void url(String locator, String value) {
        executeCommand.executeCommand(driver, Commands.OPEN_URL, null, locator, "15");
    //    executeCommand.executeCommand(driver, Commands.CLEAR, null, locator, "");
      //  executeCommand.executeCommand(driver, Commands.SEND_KEYS, null, locator, value);

    }

    public void setTextWithTab(String locator, String value) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "15");
        executeCommand.executeCommand(driver, Commands.CLEAR, null, locator, "");
        executeCommand.executeCommand(driver, Commands.SEND_KEYS, null, locator, value);

    }


    public void clearTextField(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "15");
        executeCommand.executeCommand(driver, Commands.CLEAR, null, locator, "");

    }
    public List<WebElement> getListOfElements(String locator) {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        return elements;
    }


    public void allowCameraPermission(String platform) {
        if (platform.equalsIgnoreCase("android")) {
            //String allowButton = "com.android.permissioncontroller:id/permission_allow_button";
            String allowButton = "//android.widget.Button[@text=\"Allow\" or @text=\"ALLOW\" or @text=\"Only this time\"]";
            while (isElementDisplayed(allowButton)) {
                clickButton(allowButton);
            }
        } else if (platform.equalsIgnoreCase("iOS")) {
            String allowButton = "//XCUIElementTypeButton[@name=\"OK\" or @name=\"ok\"]";
            if (isElementDisplayed(allowButton)) {
                clickButton(allowButton);
            }
        }
    }

    public void setTextUsingActions(By locator, String OTP) {
        Actions actions = new Actions(driver);
        driver.findElement(locator).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to set text using actions class");
        }
        actions.sendKeys(Keys.ENTER);
        actions.perform();
    }

    public void EnterKeyButtons(By locator) {
        Actions actions = new Actions(driver);
        driver.findElement(locator).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to set text using actions class");
        }
        actions.sendKeys(Keys.ENTER);
        actions.perform();
    }

    public void mockDeviceLocation() {
        // set the io.appium.settings app as the default mock location app for android 6 and above

        try {

            Runtime rt = Runtime.getRuntime();

            Process process = rt.exec("adb shell appops set io.appium.settings android:mock_location allow");

            String result = new BufferedReader(new InputStreamReader(process.getInputStream())).lines().collect(Collectors.joining(""));

            System.out.println("adb shell appops set io.appium.settings android:mock_location allow: " + result);


            Process enable_location_process = rt.exec("adb shell settings put secure location_providers_allowed +gps");

            String enable_location_result = new BufferedReader(new InputStreamReader(enable_location_process.getInputStream()))

                    .lines().collect(Collectors.joining(" "));

            System.out.println("adb shell settings put secure location_providers_allowed +gps: " + enable_location_result);

            //     Process location_process = rt.exec("adb shell am startservice --user 0 -n io.appium.settings/.LocationService --es longitude 106.7 --es latitude 10.8 [--es altitude 19]");
            ///     String enable_location_android = new BufferedReader(new InputStreamReader(location_process.getInputStream()))

            ///               .lines().collect(Collectors.joining(" "));

            //      System.out.println("adb location command status " + enable_location_android);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void setTextWithOutClear(String locator, String value) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "30");
        executeCommand.executeCommand(driver, Commands.SEND_KEYS, null, locator, value);

    }

    public void allowCameraPermission() {
        //String allowButton = "com.android.permissioncontroller:id/permission_allow_button";
        String allowButton = "//android.widget.Button[@text=\"Allow\" or @text=\"ALLOW\"]";
        while (isElementDisplayed(allowButton)) {
            clickButton(allowButton);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new TapException(TapExceptionType.PROCESSING_FAILED, "Failed to give camera permission");

            }
        }
    }

    public void takePictureFromCamera() throws InterruptedException {
        appiumCommands.takePictureFromAndroidCamera();
        Thread.sleep(3000);
        String corpLocator = "//android.widget.TextView[@content-desc=\"Crop\"]";
        clickButton(corpLocator);
    }

    public void takePictureFromAndroidCamera() {
        String takePicture = "//*[contains(@resource-id,'shutter_button') or contains(@text,'Shutter') or @content-desc='Capture']";
        String usePhoto = "//*[@content-desc='Done' or @content-desc='OK' or @text='OK']";
        clickButton(takePicture);
        clickButton(usePhoto);
    }


    public void scrollPageUP()  {
        Dimension size = appiumCommands.getDriver().manage().window().getSize();
        if ("iOS".equalsIgnoreCase(platform)) {
            appiumCommands.scrollElement(5000, (size.width / 2), size.height, 0, 0, -350, -100);
        } else if ("Android".equalsIgnoreCase(platform)) {
            appiumCommands.scrollElement(5000, (size.width / 2), size.height, 0, 0, -650, -100);
        }

         //   TestBasePage.driver.switchTo().defaultContent();
            testBasePage.scrollPageUP_web();
//        }

     System.out.println("Welcome to New Screen");
    }

    public boolean isElementEnabled(String locator) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "30");
        return executeCommand.executeCommand(driver, Commands.IS_ELEMENT_ENABLED, null, locator, "");
    }

    public void scrollUpTillElementDisplayed(String locator) {
        int count = 12;
        Dimension size = appiumCommands.getDriver().manage().window().getSize();
        while (!isElementDisplayed(locator) && count >= 0) {
            appiumCommands.scrollElement(5000, (size.width / 2), size.height, 0, 0, -650, -100);
            count--;
        }
    }

    public void scrollDownTillElementDisplayed(String locator) {
        int count = 9;
        Dimension size = appiumCommands.getDriver().manage().window().getSize();
        while (!isElementDisplayed(locator) && count >= 0) {
            appiumCommands.swipe(AppiumCommands.DIRECTION.DOWN, 100);
            count--;
        }
    }

    public void switchLanguage(String language) {
        String switchIcon = "//android.view.ViewGroup[1]/android.view.ViewGroup/android.widget.ImageView";
        String languageText = "";
        clickButton(switchIcon);
        switch (language) {
            case "th":
                languageText = "//android.widget.TextView[@text='ชาวไทย']";
                break;
        }
        clickButton(languageText);
    }

    public void waitTime(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (Exception e) {


        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    public void launchMailinatorUI(String mailinatorURL) {
        configvariable.setStringVariable("true", "web.driver.headless");
    //    seleniumBase.launchApplication(mailinatorURL, "", "");
        driver = SeleniumBase.driver;

    }

    public void openURL(String url) {
        executeCommand.executeCommand(driver, Commands.OPEN_URL, null, url, "");
    }



    public void launchMailinatorUI_web(String mailinatorURL) {
        platform = System.getProperty("platform.type");
        if ("desktop".equalsIgnoreCase(platform)) {
         //   seleniumBase.launchApplication(mailinatorURL);
            driver = SeleniumBase.driver;
        } else if ("iOS".equalsIgnoreCase(platform)) {

        } else if ("Android".equalsIgnoreCase(platform)) {
            driver = tapDriver.chromeBrowserDriver();
            openURL(mailinatorURL);
        }

    }

    public void clickBasedOnElementLocation(int xplus, int yplus) {
        touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.tap(PointOption.point(xplus, yplus)).perform();


    }

    public void runADBShellCommand(String command) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException var3) {
            System.out.println("Error");
            // LOGGER.error("Failed to execute ADB command");
            // throw new TapException(TapExceptionType.IO_ERROR, "Failed to execute ADB command", new Object[]{var3});
        }
    }

    public void takePictureFromIosCamera() {
        String takephoto = "//XCUIElementTypeButton[@name=\"PhotoCapture\"]";
        String usephoto = "//XCUIElementTypeStaticText[@name=\"Use Photo\"]";
        clickButton(takephoto);
        waitTime(2);
        clickButton(usephoto);
    }

    public void closeBrowser() {
        executeCommand.executeCommand(driver, Commands.CLOSE_ALL_BROWSER, null, "", "");
    }

    public void switchToFrame(String frameId) {
        executeCommand.executeCommand(driver, Commands.SWITCH_TO_FRAME, null, frameId, "");
    }

//    public void SWITCH_TO_PARENT_FRAME()
//    {
//        executeCommand.executeCommand(driver,Commands.SWITCH_TO_PARENT_FRAME,null,"");
//    }

    public void relaunchTheIosApp() {
        appiumCommands.relaunchApp();
    }

    public void relaunchTheAndroidApp() {
        appiumCommands.relaunchAndroidApp(configvariable.expandValue("${pulse.android.app.package}"), configvariable.expandValue("com.ebsdkexample.MainActivity"));
    }


    public void clickBasedOnElement(String locator, int xplus, int yplus) {
        MobileElement element = driver.findElement(By.xpath(locator));
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();
        touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.tap(PointOption.point(x + xplus, y + yplus)).perform();
    }

    public void clickSubmitClaimIconInIos(String locator) {
        MobileElement element = driver.findElement(By.xpath(locator));
        Point location = element.getLocation();
        int x = location.getX();
        int y = location.getY();
        int xplus = (int) (x*0.6);
        int yplus = (int) (y*0.5);
        touchAction = new TouchAction((PerformsTouchActions) driver);
        touchAction.tap(PointOption.point(x + xplus, y + yplus)).perform();
    }

    public void popupstart() {
        clickButton(configvariable.expandValue(Popuptexts));
        clickButton(configvariable.expandValue(popupstart));
    }

    public void setDatePickerValue(String dateText) {
        wait = new WebDriverWait(driver, 10);
        //List<WebElement> pickerEls = driver.findElements(By.xpath(configvariable.expandValue(pickers)));

        // find the picker elements
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateText, formatter);

        int actualDate = localDate.getDayOfMonth();
        String actualMonth = localDate.getMonth().name();
        int actualYear = localDate.getYear();

        if (platform.equalsIgnoreCase("android"))
            androidDatePickerScroll(new String[]{String.valueOf(actualDate), actualMonth, String.valueOf(actualYear)});
        else
            iOSDatePickerScroll(new String[]{String.valueOf(actualDate), actualMonth, String.valueOf(actualYear)});


    }

    public void tapUsingCoOrdinates(By locator, int xOffSet, int yOffSet) {
        WebElement element = appiumCommands.findElementNoWait(locator);
        Point classname = element.getLocation();
        double xcord = (double) classname.getX() + (double) xOffSet;
        double ycord = (double) classname.getY() + (double) yOffSet;
        appiumCommands.tap(xcord, ycord);
    }

    public void androidDatePickerScroll(String[] list) {
        for (int i = 0; i < list.length; i++) {
            By meX = By.xpath(configvariable.getFormattedString(configvariable.expandValue(datePickerEditField), String.valueOf((i + 1))));
            WebElement me = driver.findElement(meX);
            boolean flag = me.getText().trim().equalsIgnoreCase(list[i].trim());

            while (!flag) {
                driver.findElement(By.xpath(configvariable.getFormattedString(configvariable.expandValue(dayselectorUpButton), String.valueOf(i + 1)))).click();
                flag = me.getText().trim().equalsIgnoreCase(list[i].trim());
            }
        }
    }

    public void iOSDatePickerScroll(String[] list) {
        for (int d = 0; d < list.length; d++) {
            WebElement dateElement = driver.findElements(By.xpath(configvariable.expandValue(pickers))).get(d);
            boolean Flag = false;
            while (!Flag) {
                String valueFromPicker = dateElement.getAttribute("value");
                Flag = list[d].trim().equalsIgnoreCase(valueFromPicker);
                if (!Flag)
                    scrollToValueDatePicker(dateElement, "previous");
            }
        }
    }

    public void scrollToValueDatePicker(WebElement element, String direction) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("order", direction);
        params.put("offset", 0.15);
        params.put("element", ((RemoteWebElement) element).getId());
        if (platform.equalsIgnoreCase("ios"))
            ((IOSDriver) driver).executeScript("mobile: selectPickerWheelValue", params);


    }

    public void ScrollUpForOTP() {
        Dimension size = driver.manage().window().getSize();
        appiumCommands.scrollElement(5000, (size.width / 2), size.height, 0, 0, -500, -(size.height));
    }

    public void scrollToTheEndOfPruShoppeScreen() {
        Dimension dimensions = driver.manage().window().getSize();
//        int startingYOffset = (int) (dimensions.getHeight() * 0.8);
//        int endingYOffset = (int) (dimensions.getHeight() * 0.2);
        appiumCommands.scrollElement(500, (dimensions.getWidth()/2), dimensions.getHeight(), 0, 0, -432, -1728);
    }


    public String getElementAttributeValue(String locator, String attributeName) {
        executeCommand.executeCommand(driver, Commands.WAIT_FOR_ELEMENT_TO_PRESENT, null, locator, "10");
        executeCommand.executeCommand(driver, Commands.GET_ATTRIBUTE, null, locator, attributeName);
        return configvariable.getStringVar("element.attribute");
    }

    public void clickElementByCoordinateForBothXY(String locator, int xOffset, int yOffset) {
        appiumCommands.clickElementByCoordinateforBothXY(By.xpath(locator),xOffset,yOffset);
    }


    public void clickElementByCoordinateForBothXY(int xOffset, int yOffset) {
        appiumCommands.clickElementByCoordinateforBothXY(xOffset, yOffset);
    }

    public Map<String, Map<String, String>> loadCSVDataIntoMap(String fileName, char separator) {
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        String[] headers = csvUtils.readCSVFileHeaders(fileName);
        List<String[]> allCsvData = csvUtils.readAlldataFromCSVFile(fileName, separator);
        // print Data
        for (String[] row : allCsvData) {
            Map<String, String> rowData = new HashMap<>();
            String firstColumn = row[0];
            for (int iRowCount = 1; iRowCount < row.length; iRowCount++) {
                rowData.put(configvariable.expandValue(headers[iRowCount]), configvariable.expandValue(row[iRowCount]));
            }
            dataMap.put(configvariable.expandValue(firstColumn), rowData);
        }
        return dataMap;
    }

    public Map<String, String> loadCSVDataInto2DMap(String fileName, char separator) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        InputStream initialStream = getClass().getResourceAsStream(fileName);
        List<String[]> allCsvData = csvUtils.csvInputStreamReader(initialStream, separator);
        for (String[] row : allCsvData) {
            dataMap.put(row[0],row[1]);
        }
        return dataMap;
    }



}







