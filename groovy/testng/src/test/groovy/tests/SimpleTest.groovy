package com.blazedemo.tests

import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.testng.Assert
import org.testng.SkipException
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class SimpleTest extends BaseTest {

    @BeforeMethod
    void navigate() {
        driver.get('http://blazedemo.com/')
    }

    @Test
    void passedTest() {
        driver.findElement(By.xpath("//input[@type = 'submit']")).click()
        waiter.until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//select[@name = 'fromPort']")))
        Assert.assertEquals(driver.findElement(By.xpath("//h3")).getText(),
                'Flights from Paris to Buenos Aires:',
                'Incorrect header')
    }

    @Test
    void failedTest() {
        driver.findElement(By.xpath("//input[@type = 'submit']")).click()
        waiter.until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//select[@name = 'fromPort']")))
        Assert.assertEquals(driver.findElement(By.xpath("//h3")).getText(),
                'Flights from Paris to New Delhi:',
                'Incorrect header')
    }

    @Test
    void brokenTest() {
        driver.findElement(By.xpath("//broken-locator")).click()
        waiter.until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//select[@name = 'fromPort']")))
        Assert.assertEquals(driver.findElement(By.xpath("//h3")).getText(),
                'Flights from Paris to New Delhi:',
                'Incorrect header')
    }

    @Test
    void skippedTest() {
        throw new SkipException('Skipped test')
    }
}
