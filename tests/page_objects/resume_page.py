from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class ResumePage(BasePage):
    UPLOAD_DROPZONE = (By.ID, "resume-upload-dropzone")
    PLAIN_TEXT_AREA = (By.ID, "textarea-resume-plain-text")

    def is_upload_dropzone_displayed(self):
        return self.is_displayed(self.UPLOAD_DROPZONE)

    def enter_resume_text(self, text):
        self.clear(self.PLAIN_TEXT_AREA)
        self.send_keys(self.PLAIN_TEXT_AREA, text)

    def get_resume_text_value(self):
        return self.get_attribute(self.PLAIN_TEXT_AREA, "value") or self.get_text(self.PLAIN_TEXT_AREA)
