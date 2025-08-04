package com.onepulse.app.screens;

//import com.product.genericcomponents.appium.AppiumCommands;
//import com.product.genericcomponents.config.Configvariable;
//import com.product.genericcomponents.driver.TapDriver;
//import com.product.genericcomponents.selenium.ExecuteCommand;
//import com.product.genericcomponents.selenium.JavaScriptExec;
//import com.product.genericcomponents.selenium.SeleniumBase;
//import com.product.genericcomponents.selenium.WebTableMethods;
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

//    public String fieldLabel = "//label[text()=\"%s\"]";
//    public String divLabel = "//div[contains(text(),\"%s\")]";
//    public String employeeGivenNameLocator = "//input[@id='givenName']";
//    public String employeeMiddleNameLocator = "//input[@id='middleName']";
//    public String employeeSurNameLocator = "//input[@id='familyName']";
//    public String employeeNickNameLocator = "//input[@id='nickname']";
//    public String passportIDLocator = "//input[@id='passport']";
//    public String nationalIDLocator = "//input[@id='nationalId']";
//    public String companyEmailLocator = "//input[@id='companyEmail']";
//    public String employeeAddress1Locator = "//input[@id='address.address1']";
//    public String employeeAddress2Locator = "//input[@id='address.address2']";
//    public String employeeAddress3Locator = "//input[@id='address.address3']";
//    public String employeeAddress4Locator = "//input[@id='address.address4']";
//    public String employeeRegionLocator = "//input[@id='address.region']";
//    public String employeeCityLocator = "//input[@id='address.city']";
//    public String employeePostcodeLocator = "//input[@id='address.postCode']";
//    public String dependantGivenNameLocator = "//input[@id='givenName']";
//    public String dependantSurNameLocator = "//input[@id='familyName']";
//    //New locator for toggle but needs to be updated to only pass the rows with //td[contains(@class(),'MainRow')]
//    public String employeeExpandButton = "//tr[%d]/td[%d]//span";
//    public String employeeDataText = "//div[contains(text(),\"%s\")]/following-sibling::div[text()='%s']";
//    public String address2InformationalText = employeeAddress2Locator + "/following-sibling::div";
//    public String address3InformationalText = employeeAddress3Locator + "/following-sibling::div";
//    public String nickNameInformationalText = employeeNickNameLocator + "/following-sibling::div";
//    public String dropDownMenuList = "//div[contains(@class,\"menu-list\")]/child::div";
//    String countryTextField = "//input[@id='address.country']";
//    String countryDropDownBtn = "//div[@id='address.country_container']//div[contains(@class,\"react-select__indicators\")]";
//    String maritalStatusTextField = "//div[@id='maritalStatus_container']//div[contains(@class,\"react-select__value\")]";
//    String maritalStatusDropDownBtn = "//div[@id='maritalStatus_container']//div[contains(@class,\"react-select__indicators\")]";
//    String categoryDropDownBtn = "//div[@id='category_container']//div[contains(@class,\"react-select__indicators\")]";
//    String occupationalClassDropDownBtn = "//div[@id='occupationalClass_container']//div[contains(@class,\"react-select__indicators\")]";
//    String nationalityTextField = "//div[@id='nationality_container']//div[contains(@class,\"react-select__value-container\")]/div[1]";
//    String nationalityDropDownBtn = "//div[@id='nationality_container']//div[contains(@class,\"react-select__indicators\")]";
//    String dobDayLocator = "//input[@id='dateOfBirth_day']";
//    String dobMonthLocator = "//input[@id='dateOfBirth_month']";
//    String dobYearLocator = "//input[@id='dateOfBirth_year']";
//    String employeeDayLocator = "//input[@id='startDate_day']";
//    String employeeMonthLocator = "//input[@id='startDate_month']";
//    String employeeYearLocator = "//input[@id='startDate_year']";
//    String legendLabel = "//legend[text()=\"%s\"]";
//    String relationTextField = "//div[@id='relationshipToEmployee_container']//div[contains(@class,\"react-select__single-value\")]";
//    String relationDropDownBtn = "//div[@id='relationshipToEmployee_container']//div[contains(@class,\"react-select__indicators\")]";
//    String dependentProfileLocator = "//form[@id='%s']";
//    public String archiveCheckBox = "//input[@id='archive']";
//    public String employeeTableRowCount = "//div/table/tbody/tr/td[1]//span";
//    public String removeSearchTextButton = "//input[@placeholder='${emp.search.field}']/following-sibling::div";
//    public String h2HeadersLabel = "//h2[contains(text(),\"%s\")]";
//    public String h3HeadersLabel = "//h3[contains(text(),\"%s\")]";
//    public String empModalDivLabel = "//div[text()=\"%s\"]";
//    public String headerText = "//div/table/thead/tr[1]/td[contains(text(),\"%s\")]";
//    public String addBtnToAddAdmin = "//div[text()=\"%s\"]/following-sibling::button[text()=\"${hr.administrator.modal.add.button}\"]";
//    public String addAdminRowCount = "//div[contains(@class,'ContentRow')]";
//    public String closeAddAdminModal = "//div[text()=\"${add.administrator.modal.text}\"]//following-sibling::button";
//    //    public String actionBtnOnAdminTable = "//td[text()=\"%s\"]/following-sibling::td/div[contains(@class,\"styles__UtilityDropDown\")]";
//    public String actionBtnOnAdminTable = "(//td[contains(text(),\"%s\")]//following-sibling::td//button)[1]";
//    public String inputFieldOnConfirmationModal = fieldLabel + "//following-sibling::input[@type='text']";
//    public String closeAddAdminConfirmationModal = "//button[contains(@class,\"IconWrapper\")][2]";
//    public String tdLabel = "//td[text()=\"%s\"]";
//    public String categoryTextField = "//div[@id='category_container']//div[contains(@class,\"react-select__single-value\")]";
//    public String occupationalClassTextField = "//div[@id='occupationalClass_container']//div[contains(@class,\"react-select__value\")]";
//    public String nationalityFieldDisabledLocator = "//div[@id='nationality_container']/div[contains(@class,\"disabled\")]";
//    public String empModalErrorMessageForLegends = "//legend[text()=\"%s\"]//parent::fieldset/following-sibling::div";
//    public String empModalValidationMessageLocator = "//label[text()=\"%s\"]//following-sibling::div";
//    public String countryFieldDisabledLocator = "//input[@disabled][@id='address.country']";
//    public String empPolicyNumTextField = "//input[@id='policyNumber']";
//    public String uploadAuthorisedPersonalLetterFileInput = "//label[text()='${hr.admin.details.authorisedletter.text}']/parent::div//input[@type='file']";
//    public String administratorDetailUploadFileSection = "//span[contains(text(),\"%s\")]";
//    public String deleteDocButton = "//span[text()='%s']/parent::div/following-sibling::div/button";
//    public String uploadICFileInput = "//label[text()='${hr.admin.details.photocopyic.text}']/parent::div//input[@type='file']";
//    public String empInfoButtonLocator = "//label[text()=\"%s\"]/following-sibling::span//div[@name=\"info\"]";
//    public String memberInfoButtonLocator="//label[text()=\"%s\"]//following-sibling::div/div[@name=\"info\"]";
//    public String spanTextUploadFileSectionLocator = "//label[text()=\"%s\"]/following-sibling::div//span[text()=\"%s\"]";
//    public String divTextUploadFileSectionLocator = "//label[text()=\"%s\"]/following-sibling::div//div[text()=\"%s\"]";
//    public String buttonTextUploadFileSectionLocator = "//label[text()=\"%s\"]/following-sibling::div//button[text()=\"%s\"]";
//    public String givenNameFieldDisabledLocator = "//input[@disabled][@id='givenName']";
//    public String middleNameFieldDisabledLocator = "//input[@disabled][@id='middleName']";
//    public String surNameFieldDisabledLocator = "//input[@disabled][@id='familyName']";
//    public String positionFieldDisabledLocator = "//input[@disabled][@id='position']";
//    public String emailFieldDisabledLocator = "//input[@disabled][@id='email']";
//    public String employeePositionLocator = "//input[@id='position']";
//    public String employeeEmailLocator = "//input[@id='email']";
//    public String addAdministratorHeaderLocator = "//h3[text()=\"%s\"]//span[text()=\"%s\"]";
//    public String AddAdminSearchedCountLocator = "//div[@id=\"modal-portal\"]//button[text()=\"${hr.administrator.modal.add.button}\"]";
//    public String actionBtnOnEmpTable = "(//div[contains(text(),\"%s\")]//parent::td//following-sibling::td//div/button)[1]";
//    public String actionBtnOnMemberTable ="(//div[contains(text(),\"%s\")]//ancestor::div[@role=\"gridcell\"]/following-sibling::div//button)[1]";
//    public String positionDropDownBtn = "//div[@id='policyGroup_container']//div[contains(@class,\"react-select__indicators\")]";
//    public String positionTextField = "//div[@id='policyGroup_container']//div[contains(@class,\"react-select__value\")]";
//    public String phPassportLocator = "//input[@id='idNo']";
//    public String policyFieldDisabledLocator = "//input[@disabled][@id='policyNumber']";
//    public String errorMessageForLegends = "//legend[text()=\"%s\"]//parent::fieldset/following-sibling::div";
//    public String empModalValidationMessageLocator2 = "//label[text()=\"%s\"]//following-sibling::div[2]";
//    public String companyEmailFieldDisabledLocator = "//input[@disabled][@id='companyEmail']";
//    public String disabledActionBtnOnAdminTable = "//td[text()=\"%s\"]//following-sibling::td//button[@disabled]";
//    public String disabledActionBtnOnEmpTable = "(//div[contains(text(),\"%s\")]//parent::td//following-sibling::td//div/button[@disabled])[1]";
//    public String employeeDataTextWithEmpName ="(//div[text()=\"%s\"]/parent::td/parent::tr/following-sibling::tr//div[contains(text(),\"%s\")]/following-sibling::div[contains(text(),\"%s\")])[1]";
//    public String adminInfoButtonLocator = "//label[text()=\"%s\"]//div[@name=\"info\"]";
//    public String buttonLabelOnAddAdminModalLocator = "//div[text()=\"${doc.delete.popup.message1}\"]/parent::div/following-sibling::div//button[text()=\"%s\"]";
//    public String actionOptionOnAdminTable ="(//td[text()=\"%s\"]//following-sibling::td//button[text()=\"%s\"])[1]";
//    public String spanTypeButtonLocator = "//span[text()=\"%s\"]/parent::button";
//    public String dobLocator= "//input[@id=\"text3\"]";
//    public String empStartDateLocator="//label[@for=\"startDate\"]/following-sibling::div//input";
//    public String dobCalBtnLocator="//label[@for=\"dateOfBirth\"]/following-sibling::div//input//following-sibling::div";
//    public String empStartDateCalBtnLocator="//label[@for=\"startDate\"]/following-sibling::div//input//following-sibling::div";
//    public String yearLocator ="//input[@id=\"year\"]/preceding-sibling::div";
//    public String monthLocator ="//input[@id=\"month\"]/preceding-sibling::div";
//    public String previousMonthLocator="//div[@id=\"prev-month\"]";
//    public String nextMonthLocator="//div[@id=\"next-month\"]";
//    public String todayLocator="//div[contains(@class,\"today\")]";
//    public String adminMobileNumberLocator = "//input[@id=\"mobilePhone\"]";
//    public String adminLandLineNumberLocator = "//input[@id=\"phone\"]";
//    public String calDayLabel = "//div[text()='%s'][contains(@aria-label,'%s')]";
//    public String positionFieldDisabledLocatorEditScreen="//input[@disabled][@id='policyGroup']/preceding-sibling::div[contains(@class,disbaled)]";
//    public String deactivateReasonDropDownLocator="//input[@id='reason']/preceding-sibling::div";
//    public String stateDropDownBtn = "//div[@id='address.state_container']//div[contains(@class,\"react-select__indicators\")]";
//    public String searchTextField = "//input[@id=\"keyword\"]";
//    public String memberTable = "//div[@aria-label=\"grid\"]/div";
//    public String membersCount = "//div[contains(text(),\"%s\")]";
//    public String memberTableHeaders = "//div[@role=\"columnheader\"]";
//    public String membersRowCount = "//div[@aria-label=\"grid\"]//div[@role=\"row\"]";
//    public String membersTableColumnValueLocator = "//div[@aria-label=\"grid\"]//div[@role=\"row\"][%s]//div[@role=\"gridcell\"][%s]";
//    public String occupationalClassLocator = "//input[@id='occupationalClass']";
//    public String nationalityLocator = "//input[@id='nationality']";
//    public String membersNationalityFieldDisabledLocator = "//input[@id='nationality'][@disabled]";
//    public String nricNumberLocator= "//input[@id='nricNo']";
//    public String disabledActionBtnOnMemberTable="//div[contains(text(),\"%s\")]//ancestor::div[@role=\"gridcell\"]/following-sibling::div//button[@disabled][1]";
//    public String membersExpandButton ="//div[@aria-rowindex='%d']//div[@aria-colindex='4']/div/div/span";
//    public String membersDataTextWithName = "//div[text()=\"%s\"]/ancestor::div[@role=\"row\"]//dt[contains(text(),\"%s\")]/following-sibling::dd[text()=\"%s\"]";
//    public String stateTextLocator ="//input[@id='address.state']/preceding-sibling::div";
//
//    public String securitiesBtn = "${fb.login.username}";
//    public String reposPerWeekLogo = "${repos.per.week}";
//
//
//    public String Contact_icon = "${contact.icon}";
//    public String repoUpdatedText = "${repo.updated.text}";
//    public String FirstName = "${firstname.text}";
//    public String lastName = "${lastName.text}";
//    public String organization = "${organisation.text}";
//    public String Email = "${Email.text}";
//    public String password = "${password.text}";
//    public String phoneNumber = "${PhoneNumber.text}";
//    public String Comments = "${Comments.text}";
//    public String Submitbutton = "${submit.button}";
//    public String verifyText = "${VerifyText.button}";
//    public String LastNamemessage = "${lastNamemessage.text}";
//    public String Duplicateemailid = "${DuplicateEmailid.text}";
//    public String contactIcon = "${contacticon.text}";
//    public String GetTextvalue = "${GetText.value}";
//    public String userNameTextField = ".//input[@id=\"text1\"]";
//    public String passwordTextField = ".//input[@id=\"text3\"]";
//    public String uploadEmpFileInput = ".//input[@name=\"img\"]";
//    public String uploadSignedProposalFormFileInput = ".//input[@name=\"img\"]";
//    public String quotesTable = "//div";
//    public String claimsTable = "tr";
//    public String globalSearchList = "option";
//    public String framecheckbox = ".//td[text()=\"Cat\"]/preceding::input";
//    public String frames1 = "frame1";
//    public String frames2 = "frame2";
//    public String scrollDownElement = ".//input[@name=\"Country\"]";
//    public String SwitchTab = ".//button[text()='New Tab']";
//    public String SwitchTabs = ".//button[text()='New Window']";







    public void uploadProposalFormFile(String filePath){
        this.testBasePage.scrollTillElementFound(configvariable.expandValue(uploadSignedProposalFormFileInput));
        this.testBasePage.uploadFileUsingFileInputField(configvariable.expandValue(uploadSignedProposalFormFileInput), filePath);

    }

    public boolean isEmployeeFileDownloaded(String filepath) {
        logger.info("Downloaded file name is =" + filepath);
        return this.testBasePage.isFilePresentInFolder(filepath);
    }




    public void navigateToUrl(String url) {
        //     WebDriver driver = testBasePage.getDriver();
        testBasePage.navigateToApplicationUrl(configvariable.expandValue(url));
    }




    public void clickResourceIocn(String platform) {
        testBasePage.waitTime(3);

        List<WebElement>  dat= TestBasePage.driver.findElements(By.tagName("span"));
        System.out.println(dat.size());

        for(int i=0;i<dat.size();i++)
        {
            try {
                System.out.println(dat.get(i).getText());

                if(dat.get(i).getText().equals("WHO WE SERVE"))
                {
                    dat.get(i).click();
                    List<WebElement>  dat1= TestBasePage.driver.findElements(By.tagName("span"));
                    System.out.println("WHO WE SERVER MENU COUNT IS  ----> "+dat1.size());

                }
            }
            catch(Exception io)
            {

            }
        }



    }



























    public Response getAppIdForGithub() {
        String appointmentId = "";
        String episodeId = "";
        String patientId = "";
        //  restAPIMethods.setApiBaseUri(Configvariable.envPropertyMap.get(MyDocApiConstUtils.MYDOC_BASE_URL));
        restAPIMethods.setApiBaseUri(configvariable.expandValue(MyDocApiConstUtils.GihubLocalBase_URL));
        restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.github_localendpoint));
        //   restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
        Map<String, String> headers = new HashMap<>();
        //    headers.put("Authorization", "Bearer " + loginAccessToken);
        headers.put("Connection", "keep-alive");
       // headers.put("Content-Type","application/json");
        headers.put("Accept-Language","en-GB,en-US;q=0.9,en;q=0.8");
        headers.put("Cookie","_octo=GH1.1.6990597.1634562335; _device_id=385daa33a59368477b18b1484725481d; tz=Asia%2FSingapore; tz=Asia%2FSingapore; color_mode=%7B%22color_mode%22%3A%22light%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; user_session=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; __Host-user_session_same_site=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; logged_in=yes; dotcom_user=manivannanpannerselvam; has_recent_activity=1; _gh_sess=zJiJbdHiCXHnmWmLyTLOPKYrZ8R5XtzeZonqiRJwMA%2Frg0cNOgoRWWG4Yfxb7%2FcUEb1m2X88%2BVFnRstjZBUB3LFxgNAfDrH%2BSRvdghsJcjURyEuWtPDBW5U0yjtf3rMzkqJUihnbcS15ryzYvWTJx97XlFan%2FNu3nDCQXHhTP%2FbMXrPTkg4drdcg3Kb%2BAXzFmd3cw4kVCFKbbKsz0c%2FJ7i%2Fiaz9oiYQWK4Wivv%2BUxrj34zat33unD%2BVZoSwgohxjL6th8n7vGF1jZQiDxDwPO7XemxFjOeSzFNx%2Fq3KcJfPGc8yYqDtPqKitYzNASxPM0m9GoOrbf1GqukV9SBdeOvGaqilhVApJz1RYxXJQY7oYT%2BgSPNKhuV2qet1xdOvE%2Btzz4GnWiJiCnNvJo2QlSmnvQpTG%2BNi1r91pO2omoCtAc9cMSoMbarMeyRuAarcvAncAwyu%2FJGhQ36KzOpgzY6KGD3vzE%2FCcs%2FUc25%2F90vhId4JT7gN0Jb75XpqYqHLMnSWAThWCWSUkEuGd%2B5J%2BEMv4UF8iW6jEKi55Jmn19tLLqS4nwypTT780w2rqs4MGzopYLrSj88WlxCYS6K7ladDxBcEHEQv5Y1wvxH6GX6sbtqG%2F%2B5EO%2BTxNM4x4IbDMD7sG6biI4hhib4mTlq6oBpDkjAfwrGAL5UbqRuuZCfM5LgVs4fTimKAt7B59GDj8kike38er3ZaAgItFbtnCwNZF%2BkkEuM70OYvUQOxbyej8UsrPB4iSNgwfFsbvn4AUkA7KmNqL3T0OYU7wk00IMEXOCTXD2%2FKQisKxp%2FXQ81HVGNz2Dm1dNOS18sWqp4QkpxfKRQnRhYnxrwUtfE9%2Fz77cGsXP8VdCduWwVXQeMgA7GIX2rUluVtHArCuh%2BF3vI6ABuEHaYJDIdVWkzIEf6AcdSKkjSlsUrSH2ITfjZlowkSllAPIeySHe8%2FEaQF52iXj6zlUnyHWmail9qopZwBP8hsYLfKQz5Rnn%2FxiEc70GH%2Bs6StbxSM%2FiAeWG69VaZoMcJaFnAVV%2B39XUSCk23XYFWJxANegSXSj9TECAsRuFx2rl7pi%2FYpFg8D3skEUOAZ%2BJPgBem9PvgLmdOCpmk61OPNcg6fPCj4ZJIWPbwQpbC6BmHh3lC7Q%2FA9JPQ5qinE7paNAMzWCDLHQNfREy%2Fa9Efli4dO62gfYAngrVmw4VzgkheSZMm8WlfMlSXM%2B1kvFGzCoOqVAzm8aIqDV517YN%2Bp6mFHsLhZs%2BQEtmtuw%3D--tv%2BOO%2B8gGpZIIRob--AYzJED44Qkw1uyabjVonTw%3D%3D");

        // System.out.println(loginAccessToken);
        restAPIMethods.setHeaderParams(headers);
        Response response = restAPIMethods.sendRequest("GET");
        logger.info(restAPIMethods.getResponseAsString());

        String responseBody =restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        Elements h3s = doc.getElementsByTag("span");
        System.out.println("--------------> "+ h3s.size());
        if (h3s != null) {
            for(int j=0;j<h3s.size();j++) {
                String otp = h3s.get(j).text();
                if("manivannanpannerselvam".equals(otp)) {
                    logger.info("Found otp {}" + otp);
                    break;
                }
            }

        }
//        if ((response.getStatusCode() != 200) || (response.jsonPath().get("total") == null)) {
//            logger.error("Unable to hit MyDoc endpoint = " + configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Unable to get appointment for doctor [{}]", configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//        }

        if(response.getStatusCode() ==200)
        {
            System.out.println("Response is displayed correctly");
        }
        else
        {
            System.out.println("Response is not displayed correctly");
        }
//        int totalAppointments = response.jsonPath().get("total");
//        for (int iCount = 0; iCount < totalAppointments; iCount++) {
//            appointmentId = response.jsonPath().get("data[" + iCount + "].id").toString();
//            episodeId = response.jsonPath().get("data[" + iCount + "].episode_id").toString();
//            patientId = response.jsonPath().get("data[" + iCount + "].patient.id").toString();
//            break;
//        }
//
//        if (appointmentId.isEmpty()) {
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Count not find appointment for patient [{}]");
//        }
//
//        configvariable.setStringVariable(appointmentId, "appointment_Id");
//        logger.info("Appointment ID : " + appointmentId);
//        configvariable.setStringVariable(episodeId, "EPISODE_ID");
//        configvariable.setStringVariable(patientId, "PATIENT_ID");
        return response;
    }


    public Response getApppointmentcIdForGithub() {
        String appointmentId = "";
        String episodeId = "";
        String patientId = "";
        //  restAPIMethods.setApiBaseUri(Configvariable.envPropertyMap.get(MyDocApiConstUtils.MYDOC_BASE_URL));
        restAPIMethods.setApiBaseUri(configvariable.expandValue(MyDocApiConstUtils.GihubLocalBase_URL));
        restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.github_localendpoint));
        //   restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
        Map<String, String> headers = new HashMap<>();
        //    headers.put("Authorization", "Bearer " + loginAccessToken);
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type","application/json");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Cookie","_octo=GH1.1.6990597.1634562335; _device_id=385daa33a59368477b18b1484725481d; tz=Asia%2FSingapore; tz=Asia%2FSingapore; color_mode=%7B%22color_mode%22%3A%22light%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; has_recent_activity=1; user_session=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; __Host-user_session_same_site=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; logged_in=yes; dotcom_user=manivannanpannerselvam; _gh_sess=jVWFYo%2BLx3vmPlZob%2Fssrymvp2Z3PCSwkBclhaOkw7Lmigczko67vuCOYyIqAaq9zyKwvBCseaib7VBHhsa7QZgE40fP0H1sYT%2BXtgszXSjrYmOQllfjhiE2%2FDNoy%2BBQwUfOJ2nWXfnfmhPk4x%2FKD42wyyI9j19a1jUzVDrzOCneU6oRb4he1tG%2FTUEMP6C1eFE6l0YqfrA2gJQHHxmffkaVjxJkjxfpvyzta%2Bxh6Lf3cV3ddUluSoTVT4iBUB85QCJoRtZUs5CoMaxsqBlvO5VwBqtscfnRTSvqeK6WjrYU0WPey5K2DzLAFJIAiZmFTHyjihkgkStzHhu0DShsz9PeA50bHlq%2FMQvfi94wgeeHE%2BONzynsYMmq8KCwNiYCGchWfCGQYKgNNY9NvaKtfrH6YDwO%2Bsu9zzGU2W8%2FSIWITruYbFBTfBlEmWPOkrKIZj84bUZl1v5MRu7FaRM2yVD7Vi9KCGoqgfQrJGKn44U1xKL0Kn78eP3u3jfGY8UhsgDslG9hUOR5U4n4sHoKAspYeORkM%2Febo7gypk6CV0P3kXcuzR7Okz9EyofytZdERez12rIGcLZ%2FY%2FBatAwuv4UMAADgzXzpX2RjjW3xC8W8%2Bov8%2BkE5KkC9kAzia3eBPyec3tJkcRCepJc9jsaMHU7doSkQlgSMm9t8aB2DOf%2BVdJmNPXxXQSIrMdj7jF9ySjaT460490Xsc0pGX4VZ%2BggI2JpCSdkvgzaY8GuT1vNV7VaQFB2r7tJrsI85oRQZ--CZTShSRHnQ1KOAxp--byDsfzY1bgr%2BtytjpV9XOA%3D%3D");

        // System.out.println(loginAccessToken);
        restAPIMethods.setHeaderParams(headers);
        Response response = restAPIMethods.sendRequest("GET");
        logger.info(restAPIMethods.getResponseAsString());

        String responseBody =restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        Elements h3s = doc.getElementsByTag("span");
        System.out.println("--------------> "+ h3s.size());
        if (h3s != null) {
            for(int j=0;j<h3s.size();j++) {
                String otp = h3s.get(j).text();
                if("manivannanpannerselvam".equals(otp)) {
                    logger.info("Found otp {}" + otp);
                    break;
                }
            }

        }
//        if ((response.getStatusCode() != 200) || (response.jsonPath().get("total") == null)) {
//            logger.error("Unable to hit MyDoc endpoint = " + configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Unable to get appointment for doctor [{}]", configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//        }

        if(response.getStatusCode() ==200)
        {
            System.out.println("Response is displayed correctly");
        }
        else
        {
            System.out.println("Response is not displayed correctly");
        }
//        int totalAppointments = response.jsonPath().get("total");
//        for (int iCount = 0; iCount < totalAppointments; iCount++) {
//            appointmentId = response.jsonPath().get("data[" + iCount + "].id").toString();
//            episodeId = response.jsonPath().get("data[" + iCount + "].episode_id").toString();
//            patientId = response.jsonPath().get("data[" + iCount + "].patient.id").toString();
//            break;
//        }
//
//        if (appointmentId.isEmpty()) {
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Count not find appointment for patient [{}]");
//        }
//
//        configvariable.setStringVariable(appointmentId, "appointment_Id");
//        logger.info("Appointment ID : " + appointmentId);
//        configvariable.setStringVariable(episodeId, "EPISODE_ID");
//        configvariable.setStringVariable(patientId, "PATIENT_ID");
        return response;
    }

    public void getAppId() {
        String appointmentId = "";
        String episodeId = "";
        String patientId = "";
        //  restAPIMethods.setApiBaseUri(Configvariable.envPropertyMap.get(MyDocApiConstUtils.MYDOC_BASE_URL));
        restAPIMethods.setApiBaseUri(configvariable.expandValue(MyDocApiConstUtils.GihubLocalBase_URL));
        restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.github_localendpoint));
        //   restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
        Map<String, String> headers = new HashMap<>();
        //    headers.put("Authorization", "Bearer " + loginAccessToken);
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type","application/json");
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Cookie","_octo=GH1.1.6990597.1634562335; _device_id=385daa33a59368477b18b1484725481d; tz=Asia%2FSingapore; tz=Asia%2FSingapore; color_mode=%7B%22color_mode%22%3A%22light%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; has_recent_activity=1; user_session=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; __Host-user_session_same_site=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; logged_in=yes; dotcom_user=manivannanpannerselvam; _gh_sess=jVWFYo%2BLx3vmPlZob%2Fssrymvp2Z3PCSwkBclhaOkw7Lmigczko67vuCOYyIqAaq9zyKwvBCseaib7VBHhsa7QZgE40fP0H1sYT%2BXtgszXSjrYmOQllfjhiE2%2FDNoy%2BBQwUfOJ2nWXfnfmhPk4x%2FKD42wyyI9j19a1jUzVDrzOCneU6oRb4he1tG%2FTUEMP6C1eFE6l0YqfrA2gJQHHxmffkaVjxJkjxfpvyzta%2Bxh6Lf3cV3ddUluSoTVT4iBUB85QCJoRtZUs5CoMaxsqBlvO5VwBqtscfnRTSvqeK6WjrYU0WPey5K2DzLAFJIAiZmFTHyjihkgkStzHhu0DShsz9PeA50bHlq%2FMQvfi94wgeeHE%2BONzynsYMmq8KCwNiYCGchWfCGQYKgNNY9NvaKtfrH6YDwO%2Bsu9zzGU2W8%2FSIWITruYbFBTfBlEmWPOkrKIZj84bUZl1v5MRu7FaRM2yVD7Vi9KCGoqgfQrJGKn44U1xKL0Kn78eP3u3jfGY8UhsgDslG9hUOR5U4n4sHoKAspYeORkM%2Febo7gypk6CV0P3kXcuzR7Okz9EyofytZdERez12rIGcLZ%2FY%2FBatAwuv4UMAADgzXzpX2RjjW3xC8W8%2Bov8%2BkE5KkC9kAzia3eBPyec3tJkcRCepJc9jsaMHU7doSkQlgSMm9t8aB2DOf%2BVdJmNPXxXQSIrMdj7jF9ySjaT460490Xsc0pGX4VZ%2BggI2JpCSdkvgzaY8GuT1vNV7VaQFB2r7tJrsI85oRQZ--CZTShSRHnQ1KOAxp--byDsfzY1bgr%2BtytjpV9XOA%3D%3D");

        // System.out.println(loginAccessToken);
        restAPIMethods.setHeaderParams(headers);
        Response response = restAPIMethods.sendRequest("GET");
       // logger.info(restAPIMethods.getResponseAsString());

//        String responseBody =restAPIMethods.getResponseAsString();
//        Document doc = Jsoup.parse(responseBody);
//        Elements h3s = doc.getElementsByTag("a");
//        System.out.println("--------------> "+ h3s.size());
//        if (h3s != null) {
//            for(int j=0;j<h3s.size();j++) {
//                String otp = h3s.get(j).text();
//                if("linux".equals(otp)) {
//                    logger.info("Found otp {}------------->     " + otp);
//                    break;
//                }
//            }

       // }
//        if ((response.getStatusCode() != 200) || (response.jsonPath().get("total") == null)) {
//            logger.error("Unable to hit MyDoc endpoint = " + configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Unable to get appointment for doctor [{}]", configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
//        }

//        if(response.getStatusCode() ==200)
//        {
//            System.out.println("Response is displayed correctly");
//        }
//        else
//        {
//            System.out.println("Response is not displayed correctly");
//        }
//        int totalAppointments = response.jsonPath().get("total");
//        for (int iCount = 0; iCount < totalAppointments; iCount++) {
//            appointmentId = response.jsonPath().get("data[" + iCount + "].id").toString();
//            episodeId = response.jsonPath().get("data[" + iCount + "].episode_id").toString();
//            patientId = response.jsonPath().get("data[" + iCount + "].patient.id").toString();
//            break;
//        }
//
//        if (appointmentId.isEmpty()) {
//            throw new TapException(TapExceptionType.PROCESSING_FAILED, "Count not find appointment for patient [{}]");
//        }
//
//        configvariable.setStringVariable(appointmentId, "appointment_Id");
//        logger.info("Appointment ID : " + appointmentId);
//        configvariable.setStringVariable(episodeId, "EPISODE_ID");
//        configvariable.setStringVariable(patientId, "PATIENT_ID");
      //  return response;
    }

    public void wikipedia()  {

        testBasePage.waitTime(10);
        JavascriptExecutor js = (JavascriptExecutor) TestBasePage.driver;
           // testBasePage.scrollTillElementFound(configvariable.expandValue(scrollupbutton));
        WebElement Element = TestBasePage.driver.findElement(By.xpath(".//span[text()=\"Languages\"]"));

        // Scrolling down the page till the element is found
        js.executeScript("arguments[0].scrollIntoView();", Element);

//        List<WebElement> lan = TestBasePage.driver.findElements(By.tagName("span"));
//        System.out.println(lan.size());
//
//        for(int i=0;i<lan.size();i++)
//        {
//          //  System.out.println(lan.get(i).getText());
//            String langues= lan.get(i).getText();
//            if(langues.equals("العربية"))
//            {
//                System.out.println("equal value ------>   ");
//            }
//            else
//            {
//                System.out.println("NOT EQUAL");
//            }
//        }

    }

    public void clickRegisterButton(String buttonText) {
               testBasePage.waitTime(2);
                testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(langsfirst),buttonText));
                testBasePage.waitTime(4);

              //  TestBasePage.driver.navigate().back();

        }
    public void clicktodosButton(String buttonText) {
        testBasePage.waitTime(2);
        testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(todosbutton),buttonText));
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
        welcometext = testBasePage.getElementText(configvariable.getFormattedString(configvariable.expandValue(HollywoodName),platform));

      //  testBasePage.clickButton(configvariable.getFormattedString(configvariable.expandValue(langsfirst),buttonText));
        return welcometext;
    }

    public String getHollywooddisplayed(String dateofbirth) throws java.text.ParseException {
        String welcometextss = null;

        // testBasePage.isElementDisplayed(configvariable.getStringVar(homepageText));
       //  welcometextss = testBasePage.getElementText(configvariable.expandValue(DateofBirth));

         String hh= TestBasePage.driver.findElement(By.xpath(".//th[text()=\"Born\"]/following::td")).getText();

            //       System.out.println("Output is  "+hh);

        String[] words=hh.split("\n");//splits the string based on whitespace
//using java foreach loop to print elements of string array
     //   for(String w:words){
            for(int i=0;i<words.length;i++) {
                if(i==1) {
                    System.out.println("get the Age   "+words[i]);

                     welcometextss = words[i];
                }
            }
        return welcometextss;

    }
    public String GetspouseDetails(String platform) {
        String welcometext = "";

        welcometext = testBasePage.getElementText(configvariable.expandValue(SpouseDetails));
        return welcometext;
    }

    public String GetRepositoryName( ) {
        String repositoryName ="";
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
        return  repositoryName;
    }

    public String GetStarValuesName( ) {
        String starName ="";
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
        return  starName;
    }


    public void BranchNames( ) {
        String starName ="";
        String responseBody = restAPIMethods.getResponseAsString();
        Document doc = Jsoup.parse(responseBody);
        //  Elements h3s = doc.getElementsByTag("a");
        Elements h3s = doc.getElementsByClass("Link--primary no-underline");
        System.out.println("--------------> " + h3s.size());
        if (h3s != null) {
            for (int j = 0; j < h3s.size(); j++) {
                starName = h3s.get(j).text();
                if(j==0) {
                    logger.info("Found branch  count {}------------->     " + starName);
                }
                //     break;
                //   }
            }
        }
     //   return  starName;
    }

    public void ReleaseCount( ) {
        String starName ="";
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

    public void GetSecondRepoEndPoint() {
        String appointmentId = "";
        String episodeId = "";
        String patientId = "";
        //  restAPIMethods.setApiBaseUri(Configvariable.envPropertyMap.get(MyDocApiConstUtils.MYDOC_BASE_URL));
        restAPIMethods.setApiBaseUri(configvariable.expandValue(MyDocApiConstUtils.GihubLocalBase_URL));
        restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.SecondRepo_EndPoint));
        //   restAPIMethods.setApiEndPoint(configvariable.expandValue(MyDocApiConstUtils.MYDOC_APPOINTMENTS_ENDPOINT));
        Map<String, String> headers = new HashMap<>();
        //    headers.put("Authorization", "Bearer " + loginAccessToken);
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Cookie", "_octo=GH1.1.6990597.1634562335; _device_id=385daa33a59368477b18b1484725481d; tz=Asia%2FSingapore; tz=Asia%2FSingapore; color_mode=%7B%22color_mode%22%3A%22light%22%2C%22light_theme%22%3A%7B%22name%22%3A%22light%22%2C%22color_mode%22%3A%22light%22%7D%2C%22dark_theme%22%3A%7B%22name%22%3A%22dark%22%2C%22color_mode%22%3A%22dark%22%7D%7D; has_recent_activity=1; user_session=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; __Host-user_session_same_site=hBMpH2qRjiP_yZPuT-JinYr9C3PkCO4bI3vYMKvd1RkpeszB; logged_in=yes; dotcom_user=manivannanpannerselvam; _gh_sess=jVWFYo%2BLx3vmPlZob%2Fssrymvp2Z3PCSwkBclhaOkw7Lmigczko67vuCOYyIqAaq9zyKwvBCseaib7VBHhsa7QZgE40fP0H1sYT%2BXtgszXSjrYmOQllfjhiE2%2FDNoy%2BBQwUfOJ2nWXfnfmhPk4x%2FKD42wyyI9j19a1jUzVDrzOCneU6oRb4he1tG%2FTUEMP6C1eFE6l0YqfrA2gJQHHxmffkaVjxJkjxfpvyzta%2Bxh6Lf3cV3ddUluSoTVT4iBUB85QCJoRtZUs5CoMaxsqBlvO5VwBqtscfnRTSvqeK6WjrYU0WPey5K2DzLAFJIAiZmFTHyjihkgkStzHhu0DShsz9PeA50bHlq%2FMQvfi94wgeeHE%2BONzynsYMmq8KCwNiYCGchWfCGQYKgNNY9NvaKtfrH6YDwO%2Bsu9zzGU2W8%2FSIWITruYbFBTfBlEmWPOkrKIZj84bUZl1v5MRu7FaRM2yVD7Vi9KCGoqgfQrJGKn44U1xKL0Kn78eP3u3jfGY8UhsgDslG9hUOR5U4n4sHoKAspYeORkM%2Febo7gypk6CV0P3kXcuzR7Okz9EyofytZdERez12rIGcLZ%2FY%2FBatAwuv4UMAADgzXzpX2RjjW3xC8W8%2Bov8%2BkE5KkC9kAzia3eBPyec3tJkcRCepJc9jsaMHU7doSkQlgSMm9t8aB2DOf%2BVdJmNPXxXQSIrMdj7jF9ySjaT460490Xsc0pGX4VZ%2BggI2JpCSdkvgzaY8GuT1vNV7VaQFB2r7tJrsI85oRQZ--CZTShSRHnQ1KOAxp--byDsfzY1bgr%2BtytjpV9XOA%3D%3D");

        restAPIMethods.setHeaderParams(headers);
        Response response = restAPIMethods.sendRequest("GET");
    }


}






