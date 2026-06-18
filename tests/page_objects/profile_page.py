from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class ProfilePage(BasePage):
    CHARTS_CONTAINER = (By.ID, "profile-recharts-responsive")

    def is_charts_displayed(self):
        return self.is_displayed(self.CHARTS_CONTAINER)
