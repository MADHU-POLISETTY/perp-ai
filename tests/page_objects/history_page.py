from selenium.webdriver.common.by import By
from tests.page_objects.base_page import BasePage

class HistoryPage(BasePage):
    HISTORY_ROWS = (By.CLASS_NAME, "history-item-row")
    DELETE_BUTTON = (By.ID, "btn-delete-log-record")

    def get_history_rows(self):
        return self.find_elements(self.HISTORY_ROWS)

    def delete_history_item(self):
        self.click(self.DELETE_BUTTON)

    def is_delete_button_displayed(self):
        return self.is_displayed(self.DELETE_BUTTON)
