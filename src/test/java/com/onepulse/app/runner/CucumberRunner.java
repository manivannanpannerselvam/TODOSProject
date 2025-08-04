package com.onepulse.app.runner;

import com.github.mkolisnyk.cucumber.reporting.CucumberDetailedResults;
import com.github.mkolisnyk.cucumber.runner.ExtendedTestNGRunner;
import com.onepulse.app.screens.TestBasePage;
import com.onepulse.app.utils.FileHelper;
import com.prod.tap.appium.AppiumCommands;
import com.prod.tap.config.Configvariable;
import com.prod.tap.config.TapBeansLoad;
import com.prod.tap.email.TapEmail;
import com.prod.tap.exception.TapException;
import com.prod.tap.exception.TapExceptionType;
import com.prod.tap.filehandling.ExcelUtils;
import com.prod.tap.filehandling.FileReaderUtil;
import com.prod.tap.filehandling.ZipFolder;
import com.prod.tap.reporting.TapReporting;
import com.prod.tap.selenium.SeleniumBase;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.*;

import java.util.TimeZone;

@ComponentScan(basePackages = {"com.onepulse.app", "com.prod.tap", "com/prod/tap/tapsteps"})
@Configuration
@CucumberOptions(
        monochrome = true,
        features = "classpath:features",

        glue = {"com/onepulse/app/stepdefinitions", "com/onepulse/app/cucumberhooks", "com/prod/tap/tapsteps"},
        // tags ={"@ConsultDoctor_BookAppointment,@ConsultDoctor_Editstart,@ConsultDoctor_startchat,@ConsultDoctor_Edit_BookingAppointment,@Dietician_Negative,@Dietician,@Mycommunties,@Mycommunties_facebook,@MyContentFirst,@HealthChannel,@Legal,@MySettings"},


        tags = {"@TestWeb", "~@ignore"},


        // tags = {"@SharewithfriendsE2E,@MydocConsultationSummary,@FileValidation,@babylonAnalLump,@babylonCall995,@babylonVirusRash,@babylonHernia,@babylonRib,@Legal,@onepulseRegistration,@onepulseLogin,@PulseEditProfile,@WealthRegistration,@WealthChannel,@MydocDocsDownload,@Wealth360ViewProduct,@WealthCallMeBack,@WealthNewGoalStatus,@RubyChatbot,@HealthChannel,@MyEvent,@WealthNewGoal,@WealthEditGoalAccountType,@Mycommunties,@MySettings,@WDailyFinancialBytes,@OptionalWealthRegistration,@MyDocEndToEndTest,@StoreLocator,@PruSafeCovidCover,@PruCancer360,@PruCovid19Protection,@PruSafeDengu,@EditPrivateRetirementGoal,@EditCustomizeLifestyle"},
        plugin = {"pretty", "html:target/site/cucumber-pretty",
                "json:reports/cucumber/cucumber.json","rerun:target/failedrerun.txt"}

)



public class CucumberRunner extends ExtendedTestNGRunner {

    private static final Logger LOGGER = Logger.getLogger(CucumberRunner.class);

    private TestNGCucumberRunner testNGCucumberRunner;
    public AppiumCommands appiumCommands = new AppiumCommands();

    private Configvariable configvariable;

    private FileHelper fileHelper;
    private SeleniumBase seleniumBase;

    private ExcelUtils excelUtils ;

    private TapEmail tapEmail;

    @BeforeSuite(alwaysRun = true)
    public void setUpEnvironmentToTest() {
        // write if anything needs to be set up once before tests run. e.g. connection to database
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        LOGGER.info("Setting environment file....");
        TapBeansLoad.setConfigClass(CucumberRunner.class);

        TapBeansLoad.init();
        configvariable = (Configvariable) TapBeansLoad.getBean(Configvariable.class);
        seleniumBase = (SeleniumBase) TapBeansLoad.getBean(SeleniumBase.class);
        tapEmail = (TapEmail) TapBeansLoad.getBean(TapEmail.class);
        excelUtils = (ExcelUtils)TapBeansLoad.getBean(ExcelUtils.class);

        fileHelper = (FileHelper) TapBeansLoad.getBean(FileHelper.class);
        LOGGER.info("Setting environment file....");

        LOGGER.info("Setting locator file....");

        LOGGER.info("loading language list file....");
        fileHelper.loadLanguageListFile();

        String os = System.getProperty("os.name").toLowerCase();

    }

    @BeforeClass()
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {

        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
    }

    @AfterSuite(alwaysRun = true)
   // @AfterSuite
    public void cleanUp() {
       // testNGCucumberRunner.finish();
        // close if something enabled in @before suite. e.g. closing connection to DB
        LOGGER.info("Copying and generating reports....");
        String deviceFarmLogDir = System.getenv("DEVICEFARM_LOG_DIR");
        TapReporting.generateReportForJsonFiles(deviceFarmLogDir);

        // This should be the last, sometimes the session is killed due to no new commands
        // so it might get exception
        LOGGER.info("Quiting driver if needed....");
        if (TestBasePage.driver != null) {
            TestBasePage.driver.quit();
        }
        FileReaderUtil.deleteFile("reports/TEST1.pdf");

        TapReporting.detailedReport("reports/cucumber/cucumber.json", "TestResult-app");
      //  if (deviceFarmLogDir != null) {
            CucumberDetailedResults results = new CucumberDetailedResults();
          //  results.setOutputDirectory(deviceFarmLogDir + "/PDFreports/");
              results.setOutputDirectory("reports/PDFreports");
            results.setOutputName("TestReports-app");
            results.setSourceFile("reports/cucumber/cucumber.json");
            try {
                results.execute(true, new String[]{"pdf", "png"});
            } catch (Exception var4) {
                throw new TapException(TapExceptionType.PROCESSING_FAILED, "Not able to generate detailed report [{}]", new Object[]{var4.getMessage()});
            }

      //  }
        //        Send email
        if (System.getProperty("send.email") != null) {
            String filepath = "reports/cucumber/TestReports.zip";
            ZipFolder.zipFilesAndFolder("reports/cucumber/TestReports", filepath);
            tapEmail.sendEmail(filepath);
        }
    }
}