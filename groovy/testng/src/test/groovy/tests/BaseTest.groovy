package com.blazedemo.tests

import com.blazedemo.listeners.BlazeGridReportListener
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.FluentWait
import org.openqa.selenium.support.ui.Wait
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
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

    @BeforeTest()
    void setUpDriver() {
        DesiredCapabilities caps = new DesiredCapabilities()
        if (IS_REMOTE) {
            String key = 'api_key'
            String secret = 'api_secret'
            String hub = 'https://a.blazemeter.com/api/v4/grid/wd/hub'
            caps.setCapability('blazemeter.apiKey', key)
            caps.setCapability('blazemeter.apiSecret', secret)
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

    @AfterTest
    void tearDownDriver() {
        if (driver) {
            driver.quit()
        }
    }
}
