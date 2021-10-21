from selenium import webdriver
import pytest

# creds
API_KEY = ''
API_SECRET = ''
base = 'a.blazemeter.com'


### BlazeGrid capabilites
desired_capabilities = {
    'browserName': 'chrome',
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
    driver.execute_script("/* FLOW_MARKER test-case-start */", args)
    yield
    if request.node.rep_call.failed or request.node.rep_setup.failed:
        is_assertion = 'AssertionError' in request.node.rep_call.longrepr.reprcrash.message
        status = 'failed' if is_assertion else 'broken'
    else:
        status = 'passed'
    args = {
        'status': status,
        'message': 'test \{0}'.format(status)
    }
    driver.execute_script("/* FLOW_MARKER test-case-stop */", args)


@pytest.mark.tryfirst
def pytest_runtest_makereport(item, call, __multicall__):
    rep = __multicall__.execute()
    setattr(item, "rep_" + rep.when, rep)
    return rep
