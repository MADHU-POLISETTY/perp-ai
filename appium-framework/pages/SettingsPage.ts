import BasePage from './BasePage';

export default class SettingsPage extends BasePage {
    get headerTitle() { return '//*[contains(@text, "Settings")]'; }
    get darkModeToggle() { return '//*[@resource-id="theme-toggle-switch" or contains(@content-desc, "Theme") or contains(@text, "Dark Mode")]'; }
    get notificationToggle() { return '//*[@resource-id="notification-toggle-switch" or contains(@text, "Notifications")]'; }

    async toggleDarkMode() {
        if (await this.isDisplayed(this.darkModeToggle)) {
            await this.click(this.darkModeToggle);
        }
    }

    async toggleNotifications() {
        if (await this.isDisplayed(this.notificationToggle)) {
            await this.click(this.notificationToggle);
        }
    }
}
