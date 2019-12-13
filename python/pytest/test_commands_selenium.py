import pytest
from selenium.common.exceptions import NoSuchElementException


@pytest.mark.DemoEasy
# Find element
class TestGlobal(object):
    # Find Element By ID: [Please enter your Message] input field
    def test_id(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_id("user-message").click()

    # Find Element By Name
    def test_name(self, driver):
        driver.find_element_by_name("viewport")

    # Find Element By XPath: [Get Total] button
    def test_xpath(self, driver):
        driver.find_element_by_xpath("//*[@id='gettotal']/button").click()

    # Find Element By Link Text: [Demo Home] link
    def test_link(self, driver):
        driver.find_element_by_link_text("Demo Home")

    # Find Element By Partial Link Text: [Demo Home] link
    def test_partial_link(self, driver):
        driver.find_element_by_partial_link_text("Demo")

    # Find Element By Tag Name: [This would be your first example to start with Selenium.] header
    def test_tag_name(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_tag_name("h3")

    # Find Element By Class Name: [Single Input Field] title
    def test_class_name(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_class_name("panel-heading").click()

    # Find Element By CSS Selector: [Please enter your Message] input field
    def test_css_selector(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_css_selector("#user-message").click()

    # Clear input field: [Please enter your Message] input field
    def test_clear_input(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_id("user-message").clear()

    # Send keys into input field: [Please enter your Message] input field
    def test_send_keys(self, driver):
        driver.find_element_by_id("user-message").send_keys("TestName")

    # Get attribute from element:  [Please enter your Message] input field
    def test_get_attribute(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_id("user-message").get_attribute("text")

    # Get property:  [Please enter your Message] input field
    def test_get_property(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.find_element_by_id("user-message").get_property("text_length")

    # Clicking on the link: [Demo Home] link
    def test_click_link(self, driver):
        driver.get("https://www.seleniumeasy.com/test/")
        web_element = driver.find_element_by_link_text("Selenium Tutorials")
        return web_element.click()

    # Is element displayed: [Single Checkbox Demo] title
    def test_is_displayed(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-checkbox-demo.html")
        try:
            web_element = driver.find_element_by_class_name("panel-heading")
            return web_element.is_displayed()
        except NoSuchElementException:
            return False

    # Is element enabled: [Option 1] checkbox
    def test_is_enabled(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-checkbox-demo.html")
        try:
            web_element = driver.find_element_by_class_name("cb1-element")
            return web_element.is_enabled()
        except NoSuchElementException:
            return False

    # Is element selected: [Click on this check box] checkbox
    def test_is_selected(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-checkbox-demo.html")
        try:
            web_element = driver.find_element_by_id("isAgeSelected")
            return web_element.is_selected()
        except NoSuchElementException:
            return False



            # Click and hold element: button [Draggable 1]
            #    def test_click_and_hold(self, driver):
            #        driver.get("https://www.seleniumeasy.com/test/drag-and-drop-demo.html")
            #        driver.find_element_by_css_selector("#todrag > span:nth-child(2)")

    # Scrolling
    def test_scrolling(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")

    # Implicitly wait
    def test_implicitly_wait(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.implicitly_wait(5)

    # Get window size
    def test_get_window_size(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.get_window_size()

    # Maximize window
    def test_maximize_window(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.maximize_window()

    # Minimize window
    def test_minimize_window(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.minimize_window()

    # Refresh window
    def test_refresh_window(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.refresh()

    # Get the x, y coordinates of the window
    def test_get_window_rect(self, driver):
        driver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html")
        driver.get_window_rect()

    # Set page load timeout
    def test_set_page_load_timeout(self, driver):
        driver.set_page_load_timeout(10)

    # Set script load timeout
    def test_set_script_timeout(self, driver):
        driver.set_script_timeout(10)

    # Set window position
    def test_set_window_position(self, driver):
        driver.set_window_position(100, 0)

    # Set window rect
    def test_set_window_rect(self, driver):
        driver.set_window_rect(100, 100)

    # Set window size
    def test_set_window_size(self, driver):
        driver.set_window_size(800, 600)

    # Get current url
    def test_get_current_url(self, driver):
        driver.current_url()

    # Close window
    # def test_close_window(self, driver):
    #     driver.close()
