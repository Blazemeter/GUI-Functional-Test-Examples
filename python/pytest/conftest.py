from selenium import webdriver
import pytest

# creds
API_KEY = ''
API_SECRET = ''
base = 'a.blazemeter.com'
harborid = ''


### BlazeGrid capabilites
desired_capabilities = {
    'browserName': 'chrome',
    'blazemeter.locationId': harborid,
    'blazemeter.sessionName': 'Chrome',
}


@pytest.fixture(scope="module")
def driver():
    blazegrid_url = 'https://{}:{}@{}/api/v4/grid/wd/hub'.format(API_KEY, API_SECRET, base)
    driver = webdriver.Remote(command_executor=blazegrid_url, desired_capabilities=desired_capabilities)
    yield driver
    driver.close()
    driver.quit()


@pytest.fixture(scope="function", autouse=True)
def name_reporter(request, driver):
    args = {
        'testCaseName': request.node.name,
        'testSuiteName': request.fspath.purebasename
    }
    driver.execute_script("/* BlazeGrid test-case-start */", args)
    yield
    tests_failed = request.node.rep_call.failed or request.node.rep_setup.failed
    status = 'failed' if tests_failed else 'success'
    args = {
        'status': status,
        'message': 'test \{0}'.format(status)
    }
    driver.execute_script("/* BlazeGrid test-case-stop */", args)


@pytest.mark.tryfirst
def pytest_runtest_makereport(item, call, __multicall__):
    rep = __multicall__.execute()
    setattr(item, "rep_" + rep.when, rep)
    return rep
