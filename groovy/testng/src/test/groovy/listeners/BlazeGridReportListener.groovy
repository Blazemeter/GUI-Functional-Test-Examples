package com.blazedemo.listeners

import com.blazedemo.tests.BaseTest
import org.openqa.selenium.JavascriptExecutor
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class BlazeGridReportListener extends BaseTest implements ITestListener {

    void onTestStart(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName()
        String testCaseClass = result.getTestClass().getRealClass().getSimpleName()
        Map map = [:]
        if (result.getParameters().length > 0) {
            map.testCaseName = "$testCaseName, Params: ${Arrays.toString(result.getParameters())}" as String
        } else {
            map.testCaseName = testCaseName
        }
        map.testSuiteName = testCaseClass
        ((JavascriptExecutor) driver).executeAsyncScript('/* FLOW_MARKER test-case-start */', map)
    }

    void onTestSuccess(ITestResult result) {
        Map map = [
                status : 'success',
                message: '',
        ]
        ((JavascriptExecutor) driver).executeAsyncScript('/* FLOW_MARKER test-case-stop */', map)
    }

    void onTestFailure(ITestResult result) {
        Map map = [:]
        Throwable th = result.getThrowable()
        if (th instanceof AssertionError) {
            map.status = 'failed'
        } else {
            map.status = 'broken'
        }
        map.message = th.getMessage()
        ((JavascriptExecutor) driver).executeAsyncScript('/* FLOW_MARKER test-case-stop */', map)
    }

    void onTestSkipped(ITestResult result) {
        Map map = [
                status : 'broken',
                message: result.getThrowable().getMessage(),
        ]
        ((JavascriptExecutor) driver).executeAsyncScript('/* FLOW_MARKER test-case-stop */', map)
    }

    void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    void onStart(ITestContext context) {
    }

    void onFinish(ITestContext context) {
    }
}
