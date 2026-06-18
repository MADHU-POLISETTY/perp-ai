from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class DashboardPage(BasePage):
    SIDEBAR = (By.ID, "app-sidebar")
    WELCOME_CONTAINER = (By.ID, "prepwise-applet-container")
    CARD_TOTAL_PRACTICES = (By.ID, "card-total-practices")
    CARD_AVERAGE_SCORE = (By.ID, "card-average-score")
    CARD_LAST_SCORE = (By.ID, "card-last-score")
    CATEGORY_CARDS = (By.CLASS_NAME, "category-grid-card")
    
    NAV_DASHBOARD = (By.ID, "nav-link-dashboard")
    NAV_INTERVIEW = (By.ID, "nav-link-interview-config")
    NAV_HISTORY = (By.ID, "nav-link-history")
    NAV_RESUME = (By.ID, "nav-link-resume")
    NAV_PROFILE = (By.ID, "nav-link-profile")
    
    SIGNOUT_BUTTON = (By.ID, "btn-sidebar-signout")
    NAV_LINKS = (By.CLASS_NAME, "nav-link")

    def is_sidebar_displayed(self):
        return self.is_displayed(self.SIDEBAR)

    def get_nav_links(self):
        return self.find_elements(self.NAV_LINKS)

    def navigate_to_dashboard(self):
        self.click(self.NAV_DASHBOARD)

    def navigate_to_interview_coach(self):
        self.click(self.NAV_INTERVIEW)

    def navigate_to_history(self):
        self.click(self.NAV_HISTORY)

    def navigate_to_resume_analyzer(self):
        self.click(self.NAV_RESUME)

    def navigate_to_profile(self):
        self.click(self.NAV_PROFILE)

    def sign_out(self):
        self.click(self.SIGNOUT_BUTTON)

    def is_total_practices_card_displayed(self):
        return self.is_displayed(self.CARD_TOTAL_PRACTICES)

    def is_average_score_card_displayed(self):
        return self.is_displayed(self.CARD_AVERAGE_SCORE)

    def is_last_score_card_displayed(self):
        return self.is_displayed(self.CARD_LAST_SCORE)

    def get_category_cards_count(self):
        return len(self.find_elements(self.CATEGORY_CARDS))

    def is_welcome_header_displayed(self):
        return self.is_displayed(self.WELCOME_CONTAINER)
