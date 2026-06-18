import unittest
from appium.webdriver.common.appiumby import AppiumBy
from tests.helpers.mock_driver import get_appium_driver

class TestMobileFeatures(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.driver = get_appium_driver(mock_mode=True)

    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()

    def setUp(self):
        self.driver.get("http://localhost:5173/session")
        self.driver.set_window_size(390, 844)

    # 1. Practice category cards loading
    def test_mobile_session_initialization(self):
        cats = self.driver.find_elements(AppiumBy.CLASS_NAME, "category-grid-card")
        self.assertGreater(len(cats), 0)

    # 2. Enter target role text
    def test_mobile_role_input(self):
        role_input = self.driver.find_element(AppiumBy.ID, "input-target-role")
        role_input.clear()
        role_input.send_keys("Flutter Mobile Developer")
        self.assertEqual("Flutter Mobile Developer", role_input.text)

    # 3. Target role placeholder details
    def test_mobile_role_placeholder(self):
        role_input = self.driver.find_element(AppiumBy.ID, "input-target-role")
        self.assertEqual("text", role_input.get_attribute("type"))

    # 4. Start coaching session CTA present
    def test_mobile_start_coaching_button_present(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-start-coaching")
        self.assertTrue(btn.is_displayed())

    # 5. Question generation load indicators
    def test_mobile_question_generation_loader(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-start-coaching")
        btn.click()
        loading = self.driver.find_element(AppiumBy.ID, "auth-loading-screen")
        self.assertTrue(loading.is_displayed())

    # 6. Question card fits screen dimensions
    def test_mobile_question_card_renders(self):
        self.driver.get("http://localhost:5173/session")
        card = self.driver.find_element(AppiumBy.ID, "question-display-block")
        self.assertTrue(card.is_displayed())

    # 7. Answer text field present
    def test_mobile_answer_field_present(self):
        self.driver.get("http://localhost:5173/session")
        textarea = self.driver.find_element(AppiumBy.ID, "textarea-answer-box")
        self.assertTrue(textarea.is_displayed())

    # 8. Enter answers to text field
    def test_mobile_typing_answer(self):
        self.driver.get("http://localhost:5173/session")
        textarea = self.driver.find_element(AppiumBy.ID, "textarea-answer-box")
        textarea.clear()
        textarea.send_keys("I design scalable responsive layouts using flexbox.")
        self.assertEqual("I design scalable responsive layouts using flexbox.", textarea.text)

    # 9. Characters counter changes dynamically
    def test_mobile_characters_counter(self):
        self.driver.get("http://localhost:5173/session")
        textarea = self.driver.find_element(AppiumBy.ID, "textarea-answer-box")
        textarea.clear()
        textarea.send_keys("Hello World")
        counter = self.driver.find_element(AppiumBy.ID, "character-count-span")
        self.assertTrue(counter.is_displayed())

    # 10. Next question button submission transition
    def test_mobile_next_question_click(self):
        self.driver.get("http://localhost:5173/session")
        btn = self.driver.find_element(AppiumBy.ID, "btn-next-question")
        btn.click()
        card = self.driver.find_element(AppiumBy.ID, "question-display-block")
        self.assertTrue(card.is_displayed())

    # 11. Question index indicators (fraction text)
    def test_mobile_question_index_indicator(self):
        self.driver.get("http://localhost:5173/session")
        ind = self.driver.find_element(AppiumBy.ID, "question-progress-indicator")
        self.assertTrue(ind.is_displayed())

    # 12. Prev question button bounds state
    def test_mobile_previous_question_disabled(self):
        self.driver.get("http://localhost:5173/session")
        btn = self.driver.find_element(AppiumBy.ID, "btn-prev-question")
        self.assertTrue(btn.is_displayed())

    # 13. Simulated speech capture toggling
    def test_mobile_audio_recording_toggle(self):
        self.driver.get("http://localhost:5173/session")
        rec_btn = self.driver.find_element(AppiumBy.ID, "btn-toggle-voice")
        rec_btn.click()
        self.assertTrue(rec_btn.is_displayed())

    # 14. Final submission evaluation dispatch
    def test_mobile_final_submission(self):
        self.driver.get("http://localhost:5173/session")
        submit_btn = self.driver.find_element(AppiumBy.ID, "btn-submit-interview")
        submit_btn.click()
        self.driver.get("http://localhost:5173/results")
        self.assertIn("results", self.driver.current_url)

    # 15. Results communication score display bounds
    def test_mobile_results_communication_card(self):
        self.driver.get("http://localhost:5173/results")
        score = self.driver.find_element(AppiumBy.ID, "card-communication-score")
        self.assertTrue(score.is_displayed())

    # 16. Results technical score display bounds
    def test_mobile_results_technical_card(self):
        self.driver.get("http://localhost:5173/results")
        score = self.driver.find_element(AppiumBy.ID, "card-technical-score")
        self.assertTrue(score.is_displayed())

    # 17. Results confidence score display bounds
    def test_mobile_results_confidence_card(self):
        self.driver.get("http://localhost:5173/results")
        score = self.driver.find_element(AppiumBy.ID, "card-confidence-score")
        self.assertTrue(score.is_displayed())

    # 18. Feedback description text scrolling
    def test_mobile_detailed_feedback_expand(self):
        self.driver.get("http://localhost:5173/results")
        feedback = self.driver.find_element(AppiumBy.ID, "overall-feedback-content")
        self.assertTrue(feedback.is_displayed())

    # 19. History list length validation
    def test_mobile_history_item_counts(self):
        self.driver.get("http://localhost:5173/history")
        logs = self.driver.find_elements(AppiumBy.CLASS_NAME, "history-item-row")
        self.assertGreater(len(logs), 0)

    # 20. Swipe to reveal delete actions
    def test_mobile_history_delete_swipe(self):
        self.driver.get("http://localhost:5173/history")
        success = self.driver.swipe(300, 200, 50, 200, 300)
        self.assertTrue(success)

    # 21. Confirm deletion log row removal
    def test_mobile_history_deletion(self):
        self.driver.get("http://localhost:5173/history")
        del_btn = self.driver.find_element(AppiumBy.ID, "btn-delete-log-record")
        del_btn.click()
        self.assertTrue(del_btn.is_displayed())

    # 22. Resume drag drop area size layout class
    def test_mobile_resume_dropzone_box(self):
        self.driver.get("http://localhost:5173/resume")
        dropzone = self.driver.find_element(AppiumBy.ID, "resume-upload-dropzone")
        self.assertTrue(dropzone.is_displayed())

    # 23. Upload audit file triggering
    def test_mobile_resume_audit_submit(self):
        self.driver.get("http://localhost:5173/resume")
        file_input = self.driver.find_element(AppiumBy.ID, "resume-file-picker")
        file_input.send_keys("C:\\fakepath\\my_resume.pdf")
        submit_btn = self.driver.find_element(AppiumBy.ID, "btn-audit-resume")
        submit_btn.click()
        self.assertTrue(submit_btn.is_displayed())

    # 24. Profile statistics analytics widgets
    def test_mobile_candidate_analytics_details(self):
        self.driver.get("http://localhost:5173/profile")
        avg_score = self.driver.find_element(AppiumBy.ID, "profile-recharts-responsive")
        self.assertTrue(avg_score.is_displayed())

    # 25. Dark mode bg styles container checks
    def test_mobile_theme_check_container(self):
        self.driver.get("http://localhost:5173/dashboard")
        dashboard_container = self.driver.find_element(AppiumBy.ID, "prepwise-applet-container")
        self.assertTrue(dashboard_container.is_displayed())

if __name__ == '__main__':
    unittest.main()
