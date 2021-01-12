package com.blazedemo.tests

import org.testng.Assert
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class ParametrizedTest extends BaseTest {

    @DataProvider
    Object[][] provideUrl() {
        [
                ['http://blazedemo.com/'],
                ['https://www.google.com/'],
        ]
    }

    @Test(priority = 201, dataProvider = 'provideUrl')
    void parametrizedTest(String url) {
        driver.get(url)
        Assert.assertEquals(driver.getCurrentUrl(), url, 'Incorrect URL')
    }
}
