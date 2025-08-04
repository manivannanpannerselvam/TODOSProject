package com.onepulse.app.stepdefinitions.mobilesteps;

import com.onepulse.app.cucumberhooks.CucumberHook;
import com.onepulse.app.screens.Contactusscreen;
import com.onepulse.app.screens.TestBasePage;
//import com.product.genericcomponents.config.Configvariable;

import com.prod.tap.config.Configvariable;
import com.prod.tap.config.TapBeansLoad;
import com.prod.tap.cucumberUtils.SalesFileHelper;
import com.prod.tap.cucumberUtils.ScenarioUtils;
import com.prod.tap.database.DatabaseMethods;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.filehandling.ExcelUtils;
import com.prod.tap.filehandling.FileReaderUtil;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ContactUs {

    private DatabaseMethods databaseMethods = (DatabaseMethods) TapBeansLoad.getBean(DatabaseMethods.class);

    private Connection connection = null;


    //  private AzureStorageUtils azureStorageUtils = (AzureStorageUtils)TapBeansLoad.getBean(AzureStorageUtils.class);

    private ExcelUtils excelUtils = (ExcelUtils) TapBeansLoad.getBean(ExcelUtils.class);


    private Contactusscreen Contactusscreens = CucumberHook.context.getBean(Contactusscreen.class);

    private SalesFileHelper salesFileHelper = (SalesFileHelper) TapBeansLoad.getBean(SalesFileHelper.class);


    private Configvariable configvariable = CucumberHook.context.getBean(Configvariable.class);

    private TestBasePage testBasePage = CucumberHook.context.getBean(TestBasePage.class);

    private ScenarioUtils scenarioUtils = (ScenarioUtils) TapBeansLoad.getBean(ScenarioUtils.class);


    // private HRMailsacApi hrMailsacApi = (HRMailsacApi) TapBeansLoad.getBean(HRMailsacApi.class);


    @Given("^I Launch the Application \"([^\"]*)\"$")
    public void i_Launch_the_Application(String url) throws Throwable {

        testBasePage.launchApp();
        Contactusscreens.navigateToUrl(configvariable.expandValue(url));
        testBasePage.waitTime(3);

    }

//    @When("^I write the Data in Excel file \"([^\"]*)\"$")
//    public void i_write_the_Data_in_Excel_file(String arg1) throws Throwable {
//       // Contactusscreens.writeExcelSheel(configvariable.expandValue(arg1));
//    }

    @Then("^I write to excel file \"([^\"]*)\" into below rows for column \"([^\"]*)\" and search column name \"([^\"]*)\"$")
    public void writeToGivenRowsForGivenColumn(String filePath, String columnName, String columnNameToSearch, DataTable rowData) {
        Map<String, String> rowDataMap = rowData.asMap(String.class, String.class);
        this.excelUtils.writeToRowsForGivenColumn(this.configvariable.expandValue(filePath), 0, this.configvariable.expandValue(columnName), columnNameToSearch, rowDataMap);
    }


    @Then("^I verify downloaded file name is \"([^\"]*)\"$")
    public void verifyDownloadedFileInEmployeePage(String fileName) {
        ScenarioUtils var10000 = this.scenarioUtils;
        String var10001 = this.configvariable.expandValue(fileName);
        var10000.write("Downloaded file name=" + var10001);
        Assert.assertTrue(this.Contactusscreens.isEmployeeFileDownloaded(this.configvariable.expandValue(fileName)), "File downloaded successfully.");
    }


    @Then("^I wait (.*) sec in the HR portal page$")
    public void waitTime(int time) throws InterruptedException {
        Thread.sleep(time * 1000);
    }


    @Then("I upload the Signed Proposal file \"([^\"]*)\"")
    public void uploadSignedProposalFile(String filePath) throws InterruptedException {
        String var10000 = System.getProperty("user.dir");
        String path = var10000 + "/src/test/resources" + this.configvariable.expandValue(filePath);
        Contactusscreens.uploadProposalFormFile(path);

        Thread.sleep(8000);
    }


    @Given("^I click the \"([^\"]*)\" in todos screen$")
    public void i_click_the_in_wikipedia_screen(String arg1) throws Throwable {
        Contactusscreens.clickRegisterButton(configvariable.expandValue(arg1));
    }

    @Given("^I click the \"([^\"]*)\" in todoslist screen$")
    public void i_click_the_in_todos_screen(String arg1) throws Throwable {
        Contactusscreens.clicktodosButton(configvariable.expandValue(arg1));
    }


    @Given("^Verify the Name \"([^\"]*)\" in todos screen$")
    public void verify_the_Name_in_todos_screen(String hoolywoordNames) throws Throwable {

        String welcomePages = Contactusscreens.getHollyWoodNames(configvariable.expandValue(hoolywoordNames));
        System.out.println(welcomePages);

        Assert.assertEquals(welcomePages, hoolywoordNames);


    }


}
