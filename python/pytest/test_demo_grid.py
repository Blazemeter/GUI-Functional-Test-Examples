from selenium.webdriver.support.ui import Select
import pytest


@pytest.mark.DemoGrid
class TestDemoGrid(object):
    def test_passed(self, driver):
        driver.get("http://blazedemo.com")
        driver.find_element_by_name("toPort").click()
        select = Select(driver.find_element_by_name("toPort"))
        select.select_by_visible_text("Berlin")
        driver.find_element_by_css_selector("input.btn.btn-primary").click()

    def test_failed(self, driver):
        driver.get("http://blazedemo.com/purchase.php")
        driver.find_element_by_id("inputName").clear()
        driver.find_element_by_id("inputName").send_keys("TestName")
        text = driver.find_element_by_id("inputName").get_attribute("value")
        assert (text.__eq__("testName"))

    def test_broken(self, driver):
        driver.get("http://blazedemo.com/purchase.php")
        driver.find_element_by_id("city").click()
        driver.find_element_by_id("city").send_keys("NY")
        text = driver.find_element_by_id("city").get_attribute("value")
        if (text.__eq__("NY")):
            raise (RuntimeError("Demo script thrown an exception."))
