import BasePage from './BasePage';

export default class ForgotPasswordPage extends BasePage {
    get emailInput() { return '//android.widget.EditText[contains(@text, "Email") or contains(@hint, "Email")]'; }
    get resetPasswordButton() { return '//android.widget.Button[contains(@text, "Reset") or contains(@text, "Submit")]'; }
    get successMessage() { return '//*[contains(@text, "Reset link sent") or contains(@text, "success")]'; }

    async resetPassword(email: string) {
        await this.enterText(this.emailInput, email);
        await this.hideKeyboard();
        await this.click(this.resetPasswordButton);
    }
}
