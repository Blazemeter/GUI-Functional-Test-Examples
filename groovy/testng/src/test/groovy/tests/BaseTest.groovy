package com.blazedemo.tests

import com.blazedemo.listeners.BlazeGridReportListener
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Wait
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Listeners

import java.util.concurrent.TimeUnit

@Listeners(BlazeGridReportListener.class)
class BaseTest {
    private static final boolean IS_REMOTE = System.getProperty('isRemote').toBoolean()
    private static final String BUILD_ID = Long.toString(System.nanoTime())

    private final static ThreadLocal<RemoteWebDriver> WEB_DRIVER = new ThreadLocal<>()
    private final static ThreadLocal<Wait<WebDriver>> WAITER = new ThreadLocal<>()

    RemoteWebDriver getDriver() {
        WEB_DRIVER.get()
    }

    Wait<WebDriver> getWaiter() {
        WAITER.get()
    }

    @BeforeMethod()
    void setUpDriver() {
        DesiredCapabilities caps = new DesiredCapabilities()
        if (IS_REMOTE) {
            //your API_KEY, SECRET and PROJECT_ID for project with more than 2 parallel sessions allowed:
            String key = ''
            String secret = ''
            String projectId = ''
            String hub = 'https://a.blazemeter.com/api/v4/grid/wd/hub'
            caps.setCapability('blazemeter.apiKey', key)
            caps.setCapability('blazemeter.apiSecret', secret)
            caps.setCapability('blazemeter.projectId', projectId)
            caps.setCapability('browserName', 'chrome')
            caps.setCapability('browserVersion', '74')
            caps.setCapability('blazemeter.buildId', BUILD_ID)
            caps.setCapability('blazemeter.testName', 'Webinar test')
            WEB_DRIVER.set(new RemoteWebDriver(new URL(hub), caps))
        } else {
            WEB_DRIVER.set(new ChromeDriver() as RemoteWebDriver)
        }
        driver.manage().window().maximize()

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(15, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
        WAITER.set(wait)
    }

    @AfterMethod
    void tearDownDriver() {
        if (driver) {
            driver.quit()
        }
    }
}
