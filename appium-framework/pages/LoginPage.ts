import BasePage from './BasePage';

export default class LoginPage extends BasePage {
    get emailInput() { return '//android.widget.EditText[contains(@text, "Email") or contains(@hint, "Email")]'; }
    get passwordInput() { return '//android.widget.EditText[contains(@text, "Password") or contains(@hint, "Password")]'; }
    get loginButton() { return '//android.widget.Button[contains(@text, "Login") or @content-desc="Login"]'; }
    get registerLink() { return '//*[@text="Don\'t have an account? Register" or contains(@text, "Register")]'; }
    get forgotPasswordLink() { return '//*[@text="Forgot Password" or contains(@text, "Forgot")]'; }
    get errorText() { return '//*[@color="error" or contains(@text, "Error") or contains(@text, "invalid")]'; }

    async login(email: string, pass: string) {
        await this.enterText(this.emailInput, email);
        await this.enterText(this.passwordInput, pass);
        await this.hideKeyboard();
        await this.click(this.loginButton);
    }

    async clickRegister() {
        await this.click(this.registerLink);
    }

    async clickForgotPassword() {
        await this.click(this.forgotPasswordLink);
    }
}
