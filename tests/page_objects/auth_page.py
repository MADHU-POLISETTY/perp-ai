from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class AuthPage(BasePage):
    EMAIL_INPUT = (By.ID, "auth-email-id")
    PASSWORD_INPUT = (By.ID, "auth-password-id")
    SUBMIT_BUTTON = (By.ID, "btn-auth-submit")
    GOOGLE_BUTTON = (By.ID, "btn-google-auth")
    SWITCH_MODE_BUTTON = (By.ID, "btn-switch-auth-mode")
    BACK_BUTTON = (By.ID, "btn-auth-back")
    ERROR_CARD = (By.ID, "auth-error-card")
    AUTH_CONTAINER = (By.ID, "auth-fullscreen-container")

    def is_auth_displayed(self):
        return self.is_displayed(self.AUTH_CONTAINER)

    def enter_email(self, email):
        self.clear(self.EMAIL_INPUT)
        if email:
            self.send_keys(self.EMAIL_INPUT, email)

    def enter_password(self, password):
        self.clear(self.PASSWORD_INPUT)
        if password:
            self.send_keys(self.PASSWORD_INPUT, password)

    def click_submit(self):
        self.click(self.SUBMIT_BUTTON)

    def login(self, email, password):
        self.enter_email(email)
        self.enter_password(password)
        self.click_submit()

    def click_google_sign_in(self):
        self.click(self.GOOGLE_BUTTON)

    def switch_auth_mode(self):
        self.click(self.SWITCH_MODE_BUTTON)

    def click_back_link(self):
        self.click(self.BACK_BUTTON)

    def is_error_displayed(self):
        return self.is_displayed(self.ERROR_CARD)

    def get_error_message(self):
        return self.get_text(self.ERROR_CARD)
        
    def get_email_value(self):
        return self.get_attribute(self.EMAIL_INPUT, "value") or self.get_text(self.EMAIL_INPUT)
        
    def get_password_type(self):
        return self.get_attribute(self.PASSWORD_INPUT, "type")
