from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class InterviewPage(BasePage):
    ROLE_INPUT = (By.ID, "input-target-role")
    START_BUTTON = (By.ID, "btn-start-coaching")
    ACTIVE_SESSION = (By.ID, "interview-session-active")
    QUESTION_BLOCK = (By.ID, "question-display-block")
    ANSWER_TEXTAREA = (By.ID, "textarea-answer-box")
    SUBMIT_ANSWER_BTN = (By.ID, "btn-submit-answer")
    LOADING_INDICATOR = (By.ID, "interview-session-loading")

    def configure_role(self, role_name):
        self.clear(self.ROLE_INPUT)
        self.send_keys(self.ROLE_INPUT, role_name)

    def get_role_value(self):
        return self.get_attribute(self.ROLE_INPUT, "value") or self.get_text(self.ROLE_INPUT)

    def start_coaching(self):
        self.click(self.START_BUTTON)

    def is_session_active(self):
        return self.is_displayed(self.ACTIVE_SESSION)

    def is_question_displayed(self):
        return self.is_displayed(self.QUESTION_BLOCK)

    def enter_answer(self, answer_text):
        self.clear(self.ANSWER_TEXTAREA)
        self.send_keys(self.ANSWER_TEXTAREA, answer_text)

    def get_answer_value(self):
        return self.get_attribute(self.ANSWER_TEXTAREA, "value") or self.get_text(self.ANSWER_TEXTAREA)

    def is_loading_displayed(self):
        return self.is_displayed(self.LOADING_INDICATOR)
