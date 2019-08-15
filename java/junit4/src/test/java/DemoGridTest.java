import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DemoGridTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoGridTest.class);

    private final static String API_KEY = "";
    private final static String API_SECRET = "";
    private final static String BASE = "a.blazemeter.com";


    private final static String curl = String.format("https://%s/api/v4/grid/wd/hub", BASE);

    private static RemoteWebDriver driver;

    @Rule
    public final TestName bzmTestCaseReporter = new TestName() {

        @Override
        protected void starting(Description description) {
            Map<String, String> map = new HashMap<>();
            map.put("testCaseName", description.getMethodName());
            map.put("testSuiteName", description.getClassName());
            driver.executeAsyncScript("/* FLOW_MARKER test-case-start */", map);
        }

        @Override
        protected void succeeded(Description description) {
            if (driver != null) {
                Map<String, String> map = new HashMap<>();
                map.put("status", "success");
                map.put("message", "");
                driver.executeAsyncScript("/* FLOW_MARKER test-case-stop */", map);
            }
        }

        @Override
        protected void failed(Throwable e, Description description) {
            Map<String, String> map = new HashMap<>();
            if (e instanceof AssertionError) {
                map.put("status", "failed");
            } else {
                map.put("status", "broken");
            }
            map.put("message", e.getMessage());
            driver.executeAsyncScript("/* FLOW_MARKER test-case-stop */", map);
        }
    };

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        URL url = new URL(curl);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("blazemeter.apiKey", API_KEY);
        capabilities.setCapability("blazemeter.apiSecret", API_SECRET);

        capabilities.setCapability("blazemeter.reportName", "Demo Grid test");
        capabilities.setCapability("blazemeter.sessionName", "Chrome browser test");

//        capabilities.setCapability("blazemeter.projectId", "");
//        capabilities.setCapability("blazemeter.testId", "");
//        capabilities.setCapability("blazemeter.buildId", "randomString");
//        capabilities.setCapability("blazemeter.locationId", "harbor-");

        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "69");

        driver = new RemoteWebDriver(url, capabilities);

        String reportURL = String.format("https://%s/api/v4/grid/sessions/%s/redirect/to/report", BASE, driver.getSessionId());
        System.out.println("Report url: " + reportURL);
        openInBrowser(reportURL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void openInBrowser(String string) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                java.awt.Desktop.getDesktop().browse(new URI(string));
            } catch (Exception ex) {
                LOGGER.warn("Failed to open in browser", ex);
            }
        }
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCasePassed() {
        driver.get("http://blazedemo.com");
        driver.findElementByName("toPort").click();
        Select toPort = new Select(driver.findElementByName("toPort"));
        toPort.selectByVisibleText("Berlin");
        driver.findElementByCssSelector("input.btn.btn-primary").click();
    }

    @Test
    public void testCasesFailed() {
        driver.get("http://blazedemo.com/purchase.php");
        driver.findElementById("inputName").clear();
        driver.findElementById("inputName").sendKeys("TestName");
        String text = driver.findElementById("inputName").getAttribute("value");

        // failed assertion
        assertEquals("testName", text);
    }


    @Test
    public void testCaseBroken() {
        driver.get("http://blazedemo.com/purchase.php");
        WebElement city = driver.findElement(By.id("city"));
        city.click();
        city.sendKeys("NY");
        String text = driver.findElement(By.id("city")).getAttribute("value");
        if ("NY".equals(text)) {
            throw new RuntimeException("Demo script thrown an exception.");
        }
    }
}
