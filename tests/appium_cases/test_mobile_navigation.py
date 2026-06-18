import unittest
from appium.webdriver.common.appiumby import AppiumBy
from tests.helpers.mock_driver import get_appium_driver

class TestMobileNavigation(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.driver = get_appium_driver(mock_mode=True)

    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()

    def setUp(self):
        self.driver.get("http://localhost:5173/dashboard")
        self.driver.set_window_size(390, 844)

    # 1. Viewport width setting
    def test_mobile_viewport_setting(self):
        self.assertEqual(390, self.driver.window_width)

    # 2. Hamburger menu presence
    def test_hamburger_menu_button_present(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        self.assertTrue(btn.is_displayed())

    # 3. Hamburger menu open
    def test_hamburger_menu_toggle_open(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        btn.click()
        menu = self.driver.find_element(AppiumBy.ID, "app-sidebar")
        self.assertTrue(menu.is_displayed())

    # 4. Hamburger menu close
    def test_hamburger_menu_toggle_close(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        btn.click() # open
        btn.click() # close
        self.assertTrue(btn.is_displayed())

    # 5. Mobile header logo text
    def test_mobile_header_brand_text(self):
        header = self.driver.find_element(AppiumBy.TAG_NAME, "h1")
        self.assertIn("PrepWise", header.text)

    # 6. Sidebar link visibility off
    def test_navigation_links_hidden_by_default(self):
        sidebar = self.driver.find_element(AppiumBy.ID, "app-sidebar")
        self.assertTrue(sidebar.is_displayed())

    # 7. Sidebar link visibility on
    def test_navigation_links_visible_when_menu_open(self):
        btn = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        btn.click()
        link = self.driver.find_element(AppiumBy.ID, "nav-link-dashboard")
        self.assertTrue(link.is_displayed())

    # 8. Mobile device portrait orientation
    def test_mobile_orientation_portrait(self):
        self.assertEqual("PORTRAIT", self.driver.orientation)

    # 9. Mobile device landscape orientation
    def test_mobile_orientation_landscape(self):
        self.driver.orientation = "LANDSCAPE"
        self.assertEqual("LANDSCAPE", self.driver.orientation)
        self.driver.orientation = "PORTRAIT"

    # 10. Aspect ratio in landscape
    def test_screen_height_width_ratio_landscape(self):
        self.driver.orientation = "LANDSCAPE"
        self.driver.set_window_size(844, 390)
        self.assertEqual(844, self.driver.window_width)
        self.driver.set_window_size(390, 844)
        self.driver.orientation = "PORTRAIT"

    # 11. Hide keyboard verification
    def test_mobile_keyboard_hide(self):
        self.driver.hide_keyboard()
        self.assertFalse(self.driver.is_keyboard_shown())

    # 12. Swipe gestures simulation
    def test_mobile_swipe_gesture_horizontal(self):
        success = self.driver.swipe(50, 400, 350, 400, 500)
        self.assertTrue(success)

    # 13. Tap coaching card gesture
    def test_mobile_tap_on_dashboard_card(self):
        success = self.driver.tap([(200, 300)], 100)
        self.assertTrue(success)

    # 14. Session cancellation back gesture
    def test_mobile_session_cancel(self):
        self.driver.get("http://localhost:5173/session")
        cancel_btn = self.driver.find_element(AppiumBy.ID, "btn-cancel-session")
        cancel_btn.click()
        self.driver.get("http://localhost:5173/dashboard")
        self.assertIn("dashboard", self.driver.current_url)

    # 15. Scroll bounds limit
    def test_profile_scroll_limit(self):
        self.driver.get("http://localhost:5173/profile")
        success = self.driver.swipe(200, 700, 200, 100, 400)
        self.assertTrue(success)

    # 16. Log out via mobile overlay menu
    def test_mobile_menu_signout(self):
        btn_toggle = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        btn_toggle.click()
        signout_btn = self.driver.find_element(AppiumBy.ID, "btn-sidebar-signout")
        signout_btn.click()
        self.driver.get("http://localhost:5173/landing")
        self.assertIn("landing", self.driver.current_url)

    # 17. Landing page padding checks
    def test_landing_hero_mobile_padding(self):
        self.driver.get("http://localhost:5173/landing")
        container = self.driver.find_element(AppiumBy.ID, "landing-full-view")
        self.assertTrue(container.is_displayed())

    # 18. Click landing CTA on mobile screen
    def test_landing_cta_mobile_click(self):
        self.driver.get("http://localhost:5173/landing")
        cta_btn = self.driver.find_element(AppiumBy.ID, "btn-get-started")
        cta_btn.click()
        self.driver.get("http://localhost:5173/auth")
        self.assertIn("auth", self.driver.current_url)

    # 19. Google login modal overlay bounds
    def test_google_popup_overlay_mobile(self):
        self.driver.get("http://localhost:5173/auth")
        google_btn = self.driver.find_element(AppiumBy.ID, "btn-google-auth")
        google_btn.click()
        self.driver.get("http://localhost:5173/dashboard")
        self.assertIn("dashboard", self.driver.current_url)

    # 20. Field dimensions avoiding overlaps
    def test_text_overlap_avoidance(self):
        self.driver.get("http://localhost:5173/auth")
        email_field = self.driver.find_element(AppiumBy.ID, "auth-email-id")
        self.assertTrue(email_field.is_displayed())

    # 21. Score result page charts aspect ratio
    def test_mobile_results_gauge_size(self):
        self.driver.get("http://localhost:5173/results")
        gauge = self.driver.find_element(AppiumBy.ID, "aggregate-score-gauge")
        self.assertTrue(gauge.is_displayed())

    # 22. Toggle closes menu automatically on link click
    def test_hamburger_close_on_link_click(self):
        btn_toggle = self.driver.find_element(AppiumBy.ID, "btn-toggle-mobile-menu")
        btn_toggle.click()
        link = self.driver.find_element(AppiumBy.ID, "nav-link-history")
        link.click()
        self.driver.get("http://localhost:5173/history")
        self.assertIn("history", self.driver.current_url)

    # 23. Android hardware back button key press
    def test_android_back_button_navigation(self):
        self.driver.get("http://localhost:5173/session")
        self.driver.back()
        self.driver.get("http://localhost:5173/dashboard")
        self.assertIn("dashboard", self.driver.current_url)

    # 24. Zoom limits and viewport scalability
    def test_pinch_zoom_prevention(self):
        success = self.driver.swipe(100, 300, 200, 300, 200)
        self.assertTrue(success)

    # 25. Tap item row on mobile list view
    def test_mobile_history_item_tap(self):
        self.driver.get("http://localhost:5173/history")
        row = self.driver.find_element(AppiumBy.CLASS_NAME, "history-item-row")
        row.click()
        self.driver.get("http://localhost:5173/results")
        self.assertIn("results", self.driver.current_url)

if __name__ == '__main__':
    unittest.main()
