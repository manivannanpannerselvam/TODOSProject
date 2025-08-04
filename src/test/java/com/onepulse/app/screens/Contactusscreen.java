package com.onepulse.app.screens;


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.prod.tap.api.RestAPIMethods;
import com.prod.tap.appium.AppiumCommands;
import com.prod.tap.config.Configvariable;
import com.prod.tap.driver.TapDriver;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.selenium.*;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Contactusscreen {


    private final static Logger logger = Logger.getLogger(Contactusscreen.class);

    @Autowired
    private TestBasePage testBasePage;

    public static WebDriver driver;

    public static String platform;

    public static String language;

    private static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

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
    private AppiumCommands appiumCommands;

    @Autowired
    public JavaScriptExec JavaScriptExec;

    @Autowired
    private RestAPIMethods restAPIMethods;

    public static Response response;
    public static int LoginID;


    public String HollywoodName = "${HoolywoorName.icons}";
    public String searchIcons = "${Search.icons}";
    public String EnterNames = "${EnterNames.text}";
    public String langsecond = "${secondlang.button}";
    public String langsfirst = "${firstLang.button}";
    public String todosbutton = "${todos.button}";

    public String employeePageButtonLabel = "//button[text()='%s']";


    public void navigateToUrl(String url) {
        //     WebDriver driver = testBasePage.getDriver();
        testBasePage.navigateToApplicationUrl(configvariable.expandValue(url));
    }


    public void clickResourceIocn(String platform) {
        testBasePage.waitTime(3);

        List<WebElement> dat = TestBasePage.driver.findElements(By.tagName("span"));
        System.out.println(dat.size());

        for (int i = 0; i < dat.size(); i++) {
            try {
                System.out.println(dat.get(i).getText());

                if (dat.get(i).getText().equals("WHO WE SERVE")) {
                    dat.get(i).click();
                    List<WebElement> dat1 = TestBasePage.driver.findElements(By.tagName("span"));
                    System.out.println("WHO WE SERVER MENU COUNT IS  ----> " + dat1.size());

                }
            } catch (Exception io) {

            }
        }


    }


    public void clickRegisterButton(String buttonText) {
        testBasePage.waitTime(2);
        testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(langsfirst), buttonText));
        testBasePage.waitTime(4);

        //  TestBasePage.driver.navigate().back();

    }

    public void clicktodosButton(String buttonText) {
        testBasePage.waitTime(2);
        testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(todosbutton), buttonText));
        testBasePage.waitTime(4);

        //  TestBasePage.driver.navigate().back();

    }

    public String getWelcomePageTextsss(String platform) {
        String welcometext = "";

        // testBasePage.isElementDisplayed(configvariable.getStringVar(homepageText));
        welcometext = testBasePage.getElementText(configvariable.expandValue(langsecond));


        return welcometext;
    }

    public void EnterNames(String registrationDetails) {

        testBasePage.setTextWithTab(configvariable.expandValue(EnterNames), registrationDetails);

        testBasePage.EnterKeyButtons(By.xpath(configvariable.expandValue(EnterNames)));


        // testBasePage.clickButton(configvariable.expandValue(searchIcons));
    }

    public String getHollyWoodNames(String platform) {
        String welcometext = "";
        testBasePage.waitTime(2);
        // testBasePage.isElementDisplayed(configvariable.getStringVar(homepageText));
        welcometext = testBasePage.getElementText(configvariable.getFormattedString(configvariable.expandValue(HollywoodName), platform));

        //  testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(langsfirst),buttonText));
        return welcometext;
    }

    public String getHollywooddisplayed(String dateofbirth) throws java.text.ParseException {
        String welcometextss = null;

        // testBasePage.isElementDisplayed(configvariable.getStringVar(homepageText));
        //  welcometextss = testBasePage.getElementText(configvariable.expandValue(DateofBirth));

        String hh = TestBasePage.driver.findElement(By.xpath(".//th[text()=\"Born\"]/following::td")).getText();

        //       System.out.println("Output is  "+hh);

        String[] words = hh.split("\n");//splits the string based on whitespace
//using java foreach loop to print elements of string array
        //   for(String w:words){
        for (int i = 0; i < words.length; i++) {
            if (i == 1) {
                System.out.println("get the Age   " + words[i]);

                welcometextss = words[i];
            }
        }
        return welcometextss;

    }



    public String GetRepositoryName() {
        String repositoryName = "";
        String responseBody = restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        //  Elements h3s = doc.getElementsByTag("a");
        Elements h3s = doc.getElementsByClass("mr-2 flex-self-stretch");
        System.out.println("--------------> " + h3s.size());
        if (h3s != null) {
            for (int j = 0; j < h3s.size(); j++) {
                repositoryName = h3s.get(j).text();
                //   if ("linux".equals(repositoryName)) {
                logger.info("Found REPOSITORY {}------------->     " + repositoryName);
                //     break;
                //   }
            }
        }
        return repositoryName;
    }

    public String GetStarValuesName() {
        String starName = "";
        String responseBody = restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        //  Elements h3s = doc.getElementsByTag("a");
        Elements h3s = doc.getElementsByClass("social-count js-social-count");
        System.out.println("--------------> " + h3s.size());
        if (h3s != null) {
            for (int j = 0; j < h3s.size(); j++) {
                starName = h3s.get(j).text();
                //   if ("122k".equals(otp)) {
                logger.info("Found STAR count {}------------->     " + starName);
                //     break;
                //   }
            }
        }
        return starName;
    }


    public void BranchNames() {
        String starName = "";
        String responseBody = restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        //  Elements h3s = doc.getElementsByTag("a");
        Elements h3s = doc.getElementsByClass("Link--primary no-underline");
        System.out.println("--------------> " + h3s.size());
        if (h3s != null) {
            for (int j = 0; j < h3s.size(); j++) {
                starName = h3s.get(j).text();
                if (j == 0) {
                    logger.info("Found branch  count {}------------->     " + starName);
                }
                //     break;
                //   }
            }
        }
        //   return  starName;
    }

    public void ReleaseCount() {
        String starName = "";
        String responseBody = restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        //  Elements h3s = doc.getElementsByTag("a");
        Elements h3s = doc.getElementsByClass("ml-3 Link--primary no-underline");
        System.out.println("--------------> " + h3s.size());
        if (h3s != null) {
            for (int j = 0; j < h3s.size(); j++) {
                starName = h3s.get(j).text();

                logger.info("Found Release  count {}------------->     " + starName);

                //     break;
                //   }
            }
        }
        //   return  starName;
    }



}






