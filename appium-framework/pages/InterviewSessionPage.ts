import BasePage from './BasePage';

export default class InterviewSessionPage extends BasePage {
    get targetRoleInput() { return '//android.widget.EditText[contains(@text, "Role") or contains(@hint, "Role") or @resource-id="input-target-role"]'; }
    get startCoachingButton() { return '//*[@text="Start Coaching" or @resource-id="btn-start-coaching"]'; }
    get loadingIndicator() { return '//android.widget.ProgressBar or @resource-id="auth-loading-screen"'; }

    async configureRole(roleName: string) {
        if (await this.isDisplayed(this.targetRoleInput)) {
            await this.enterText(this.targetRoleInput, roleName);
            await this.hideKeyboard();
        }
    }

    async startSession() {
        await this.click(this.startCoachingButton);
    }
}
