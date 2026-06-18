from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class LandingPage(BasePage):
    HEADER_SIGN_IN = (By.ID, "btn-header-login")
    HERO_GET_STARTED = (By.ID, "btn-hero-primary")
    CTA_GET_STARTED = (By.ID, "btn-cta-action")
    LANDING_CONTAINER = (By.ID, "landing-page-container")

    def is_landing_displayed(self):
        return self.is_displayed(self.LANDING_CONTAINER)

    def click_sign_in_header(self):
        self.click(self.HEADER_SIGN_IN)

    def click_get_started_hero(self):
        self.click(self.HERO_GET_STARTED)

    def click_get_started_cta(self):
        self.click(self.CTA_GET_STARTED)
