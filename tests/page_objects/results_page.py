from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class ResultsPage(BasePage):
    SCORE_GAUGE = (By.ID, "aggregate-score-gauge")
    FEEDBACK_CONTENT = (By.ID, "overall-feedback-content")
    DONE_BUTTON = (By.ID, "btn-results-done")

    def is_score_gauge_displayed(self):
        return self.is_displayed(self.SCORE_GAUGE)

    def is_feedback_displayed(self):
        return self.is_displayed(self.FEEDBACK_CONTENT)

    def click_done(self):
        if self.is_displayed(self.DONE_BUTTON):
            self.click(self.DONE_BUTTON)
