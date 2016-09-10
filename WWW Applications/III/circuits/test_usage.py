__author__ = 'mstankiewicz'

import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys

class CircuitsTest(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_change(self):
        driver = self.driver
        driver.get("http://127.0.0.1:8000/circuits/circuits/20206/")
        edit_button = driver.find_element_by_id("edit1254")
        edit_button.click()
        bgi = driver.find_element_by_id("bg1254")
        otvi = driver.find_element_by_id("atv1254")
        bgi.clear()
        bgi.send_keys("112")
        bgi.send_keys(Keys.RETURN)
        otvi.clear()
        otvi.send_keys("332")
        otvi.send_keys(Keys.RETURN)
        save_button = driver.find_element_by_id("save1254")
        save_button.click()
        new_bgi = driver.find_element_by_id("bg1254")
        new_otvi = driver.find_element_by_id("atv1254")
        bgi_val = new_bgi.get_attribute("value")
        otvi_val = new_otvi.get_attribute("value")
        driver.implicitly_wait(10)
        assert int(bgi_val) == 112
        assert int(otvi_val) == 332

    def test_negative(self):
        driver = self.driver
        driver.get("http://127.0.0.1:8000/circuits/circuits/20206/")
        edit_button = driver.find_element_by_id("edit1254")
        edit_button.click()
        bgi = driver.find_element_by_id("bg1254")
        otvi = driver.find_element_by_id("atv1254")
        bgi.clear()
        bgi.send_keys("-1")
        bgi.send_keys(Keys.RETURN)
        otvi.clear()
        otvi.send_keys("-1")
        otvi.send_keys(Keys.RETURN)
        save_button = driver.find_element_by_id("save1254")
        save_button.click()
        new_bgi = driver.find_element_by_id("bg1254")
        new_otvi = driver.find_element_by_id("atv1254")
        bgi_val = new_bgi.get_attribute("value")
        otvi_val = new_otvi.get_attribute("value")
        error_info = driver.find_element_by_id("error_message").text
        assert "Dałeś ujemne" in error_info

    def test_double_change(self):
        driver = self.driver
        driver2 = webdriver.Firefox()

        driver.get("http://127.0.0.1:8000/circuits/circuits/20206/")
        driver2.get("http://127.0.0.1:8000/circuits/circuits/20206/")

        edit_button1 = driver.find_element_by_id("edit1254")
        edit_button1.click()
        bgi1 = driver.find_element_by_id("bg1254")
        otvi1 = driver.find_element_by_id("atv1254")
        bgi1.clear()
        bgi1.send_keys("1")
        bgi1.send_keys(Keys.RETURN)
        otvi1.clear()
        otvi1.send_keys("1")
        otvi1.send_keys(Keys.RETURN)
        save_button1 = driver.find_element_by_id("save1254")

        edit_button2 = driver2.find_element_by_id("edit1254")
        edit_button2.click()
        bgi2 = driver2.find_element_by_id("bg1254")
        otvi2 = driver2.find_element_by_id("atv1254")
        bgi2.clear()
        bgi2.send_keys("2")
        bgi2.send_keys(Keys.RETURN)
        otvi2.clear()
        otvi2.send_keys("2")
        otvi2.send_keys(Keys.RETURN)
        save_button2 = driver2.find_element_by_id("save1254")

        save_button1.click()
        driver.implicitly_wait(6)
        save_button2.click()

        new_bgi1 = driver.find_element_by_id("bg1254")
        new_otvi1 = driver.find_element_by_id("atv1254")
        bgi_val1 = new_bgi1.get_attribute("value")
        otvi_val1 = new_otvi1.get_attribute("value")

        new_bgi2 = driver2.find_element_by_id("bg1254")
        new_otvi2 = driver2.find_element_by_id("atv1254")
        bgi_val2 = new_bgi2.get_attribute("value")
        otvi_val2 = new_otvi2.get_attribute("value")

        error_info = driver2.find_element_by_id("error_message").text
        assert "Ktoś cię wyprzedził" in error_info

        driver2.close()

    def tearDown(self):
        self.driver.close()

if __name__ == "__main__":
    unittest.main()