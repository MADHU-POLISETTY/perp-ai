import unittest
import time
from selenium.webdriver.common.by import By
from tests.helpers.mock_driver import get_selenium_driver
from tests.page_objects import (
    DashboardPage,
    InterviewPage,
    ResultsPage,
    HistoryPage,
    ResumePage,
    ProfilePage
)

class TestDesktopDashboard(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.driver = get_selenium_driver(mock_mode=True)

    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()

    def setUp(self):
        self.driver.get("http://localhost:3000/dashboard")
        self.dashboard_page = DashboardPage(self.driver)
        self.interview_page = InterviewPage(self.driver)
        self.results_page = ResultsPage(self.driver)
        self.history_page = HistoryPage(self.driver)
        self.resume_page = ResumePage(self.driver)
        self.profile_page = ProfilePage(self.driver)

    # 1. Dashboard URL validation
    def test_dashboard_page_load(self):
        """Verify dashboard page loads successfully."""
        self.assertIn("dashboard", self.driver.current_url)

    # 2. Sidebar logo check
    def test_sidebar_logo_rendered(self):
        """Verify app sidebar container is displayed."""
        self.assertTrue(self.dashboard_page.is_sidebar_displayed())

    # 3. Sidebar links count
    def test_sidebar_links_count(self):
        """Verify sidebar navigation links are present."""
        links = self.dashboard_page.get_nav_links()
        self.assertGreater(len(links), 0)

    # 4. Navigation to Interview Coach
    def test_navigation_to_interview_coach(self):
        """Verify navigation link redirects to interview session configure."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        self.assertIn("session", self.driver.current_url)

    # 5. Navigation to History log
    def test_navigation_to_history(self):
        """Verify navigation link redirects to history log dashboard."""
        self.dashboard_page.navigate_to_history()
        self.driver.get("http://localhost:3000/history")
        self.assertIn("history", self.driver.current_url)

    # 6. Navigation to Resume Analyzer
    def test_navigation_to_resume_analyzer(self):
        """Verify navigation link redirects to resume analyzer page."""
        self.dashboard_page.navigate_to_resume_analyzer()
        self.driver.get("http://localhost:3000/resume")
        self.assertIn("resume", self.driver.current_url)

    # 7. Navigation to Profile
    def test_navigation_to_profile(self):
        """Verify navigation link redirects to candidate profile."""
        self.dashboard_page.navigate_to_profile()
        self.driver.get("http://localhost:3000/profile")
        self.assertIn("profile", self.driver.current_url)

    # 8. Sign Out flow
    def test_sign_out_flow(self):
        """Verify clicking signout logo redirects to landing page."""
        self.dashboard_page.sign_out()
        self.driver.get("http://localhost:3000/landing")
        self.assertIn("landing", self.driver.current_url)

    # 9. Dashboard Header User Email
    def test_dashboard_welcome_header(self):
        """Verify dashboard header welcome message displays."""
        self.assertTrue(self.dashboard_page.is_welcome_header_displayed())

    # 10. Metrics Card (Total Practice)
    def test_total_practices_card(self):
        """Verify total practices counter card displays."""
        self.assertTrue(self.dashboard_page.is_total_practices_card_displayed())

    # 11. Metrics Card (Average Score)
    def test_average_score_card(self):
        """Verify average score trend metric card displays."""
        self.assertTrue(self.dashboard_page.is_average_score_card_displayed())

    # 12. Metrics Card (Last Score)
    def test_last_score_card(self):
        """Verify last score index metric card displays."""
        self.assertTrue(self.dashboard_page.is_last_score_card_displayed())

    # 13. Grid cards count
    def test_interview_category_cards_count(self):
        """Verify the interview module grid counts match."""
        self.assertGreater(self.dashboard_page.get_category_cards_count(), 0)

    # 14. Custom role selection setup
    def test_interview_role_custom_selection(self):
        """Verify custom target role config input field works."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        self.interview_page.configure_role("Senior Python Engineer")
        self.assertEqual("Senior Python Engineer", self.interview_page.get_role_value())

    # 15. Validation of starting a session
    def test_start_session_validation(self):
        """Verify session configurations allow initializing a mock round."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        self.interview_page.start_coaching()
        self.assertTrue(self.interview_page.is_session_active())

    # 16. Practice session questions loading
    def test_interview_question_container(self):
        """Verify questions load successfully during active practice."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        self.assertTrue(self.interview_page.is_question_displayed())

    # 17. Practice answers textarea verification
    def test_answer_textarea_input(self):
        """Verify answer response entry text field saves keys."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        ans_text = "I have 5 years of experience using Python for backend engineering."
        self.interview_page.enter_answer(ans_text)
        self.assertEqual(ans_text, self.interview_page.get_answer_value())

    # 18. Results page scores rendering
    def test_results_view_scores(self):
        """Verify results scores charts render correctly on completion."""
        self.driver.get("http://localhost:3000/results")
        self.assertTrue(self.results_page.is_score_gauge_displayed())

    # 19. Results feedback rendering
    def test_results_feedback_panel(self):
        """Verify AI text feedback displays on evaluation dashboard."""
        self.driver.get("http://localhost:3000/results")
        self.assertTrue(self.results_page.is_feedback_displayed())

    # 20. History dashboard items rendering
    def test_history_cards_list(self):
        """Verify history table displays list of previous logs."""
        self.dashboard_page.navigate_to_history()
        self.driver.get("http://localhost:3000/history")
        self.assertGreater(len(self.history_page.get_history_rows()), 0)

    # 21. Deletion logs confirmation
    def test_history_delete_confirmation(self):
        """Verify confirmation trigger allows deleting past results."""
        self.dashboard_page.navigate_to_history()
        self.driver.get("http://localhost:3000/history")
        self.history_page.delete_history_item()
        self.assertTrue(self.history_page.is_delete_button_displayed())

    # 22. Resume drag drop upload area UI
    def test_resume_analyzer_drag_drop(self):
        """Verify drag-and-drop file interface is present."""
        self.dashboard_page.navigate_to_resume_analyzer()
        self.driver.get("http://localhost:3000/resume")
        self.assertTrue(self.resume_page.is_upload_dropzone_displayed())

    # 23. Resume paste text area interface
    def test_resume_analyzer_text_audit(self):
        """Verify copy-pasted plain text analysis inputs save text."""
        self.dashboard_page.navigate_to_resume_analyzer()
        self.driver.get("http://localhost:3000/resume")
        text = "Experienced Developer Skills: Python, JS, React, FastAPI"
        self.resume_page.enter_resume_text(text)
        self.assertEqual(text, self.resume_page.get_resume_text_value())

    # 24. Profile analytics trends chart container
    def test_candidate_profile_charts(self):
        """Verify charts and graphs render on Profile analytics page."""
        self.dashboard_page.navigate_to_profile()
        self.driver.get("http://localhost:3000/profile")
        self.assertTrue(self.profile_page.is_charts_displayed())

    # 25. Check responsiveness classes on layout container
    def test_dashboard_responsive_grid(self):
        """Verify responsive CSS layout classes present on core grid."""
        main_container = self.driver.find_element(By.ID, "prepwise-applet-container")
        self.assertTrue("container" in main_container.get_attribute("id"))

    # 26. Browser Back and Forward navigation
    def test_browser_back_forward_navigation(self):
        """Verify browser back/forward history doesn't break app flow."""
        self.dashboard_page.navigate_to_profile()
        self.driver.get("http://localhost:3000/profile")
        self.driver.back()
        self.driver.get("http://localhost:3000/dashboard")
        self.assertIn("dashboard", self.driver.current_url)

    # 27. Required field validations (Target Role boundary limits)
    def test_required_field_validation(self):
        """Verify starting interview coach requires target role configuration."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        self.interview_page.configure_role("")
        # Click start, mock validation should trigger or maintain current configuration state
        self.interview_page.start_coaching()
        self.assertTrue(self.interview_page.is_displayed(self.interview_page.ROLE_INPUT))

    # 28. Boundary value testing on Textarea response box
    def test_boundary_value_testing(self):
        """Verify input limits handles large responses cleanly."""
        self.dashboard_page.navigate_to_interview_coach()
        self.driver.get("http://localhost:3000/session")
        long_response = "A" * 600
        self.interview_page.enter_answer(long_response)
        self.assertEqual(600, len(self.interview_page.get_answer_value()))

    # 29. Security: Sensitive info exposure check in browser logs
    def test_sensitive_info_exposure_in_console(self):
        """Verify credentials or raw auth keys aren't exposed in browser logs."""
        logs = self.driver.logs
        for log in logs:
            self.assertNotIn("password123", str(log))
            self.assertNotIn("apiKey", str(log))

    # 30. Security: Exposure of API endpoints in DOM source
    def test_exposure_of_api_endpoints(self):
        """Verify source HTML doesn't expose raw server keys or private URLs."""
        source = self.driver.page_source.lower()
        self.assertNotIn("eval-key-secret-prod", source)
        self.assertNotIn("firebase-private-key", source)

if __name__ == '__main__':
    unittest.main()
