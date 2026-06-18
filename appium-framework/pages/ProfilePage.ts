import BasePage from './BasePage';

export default class ProfilePage extends BasePage {
    get headerTitle() { return '//android.widget.TextView[@text="Profile"]'; }
    get logoutIcon() { return '//*[@content-desc="Logout"]'; }
    get logoutButton() { return '//android.widget.Button[@text="Logout"]'; }
    get editProfileButton() { return '//android.widget.Button[@text="Edit Profile"]'; }
    get userNameText() { return '//android.widget.TextView[@resource-id="user-profile-name" or @text="User Name" or @text!=""]'; }

    async logoutViaIcon() {
        await this.click(this.logoutIcon);
    }

    async logoutViaButton() {
        await this.click(this.logoutButton);
    }

    async clickEditProfile() {
        await this.click(this.editProfileButton);
    }
}
