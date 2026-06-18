import BasePage from './BasePage';

export default class RegistrationPage extends BasePage {
    get nameInput() { return '//android.widget.EditText[contains(@text, "Name") or contains(@hint, "Name")]'; }
    get emailInput() { return '//android.widget.EditText[contains(@text, "Email") or contains(@hint, "Email")]'; }
    get passwordInput() { return '//android.widget.EditText[contains(@text, "Password") or contains(@hint, "Password")][1]'; }
    get confirmPasswordInput() { return '//android.widget.EditText[contains(@text, "Confirm") or contains(@hint, "Confirm")]'; }
    get registerButton() { return '//android.widget.Button[contains(@text, "Register") or contains(@text, "Sign Up")]'; }
    get backToLoginButton() { return '//*[contains(@text, "Already have an account") or contains(@text, "Login")]'; }

    async register(name: string, email: string, pass: string) {
        await this.enterText(this.nameInput, name);
        await this.enterText(this.emailInput, email);
        await this.enterText(this.passwordInput, pass);
        await this.enterText(this.confirmPasswordInput, pass);
        await this.hideKeyboard();
        await this.click(this.registerButton);
    }
}
