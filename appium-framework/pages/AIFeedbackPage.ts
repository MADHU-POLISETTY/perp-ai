import BasePage from './BasePage';

export default class AIFeedbackPage extends BasePage {
    get performanceTitle() { return '//*[@text="Performance Result"]'; }
    get scorePercentage() { return '//android.widget.TextView[contains(@text, "%")]'; }
    get strongAreasSection() { return '//*[@text="Strong Areas"]'; }
    get improvementAreasSection() { return '//*[@text="Areas for Improvement"]'; }
    
    // Actions
    get backToHomeButton() { return '//android.widget.Button[@text="Back to Home"]'; }
    get detailedFeedbackButton() { return '//android.widget.Button[@text="View Detailed AI Feedback"]'; }

    async getScore(): Promise<string> {
        return await this.getText(this.scorePercentage);
    }

    async clickBackToHome() {
        await this.click(this.backToHomeButton);
    }

    async clickDetailedFeedback() {
        await this.click(this.detailedFeedbackButton);
    }
}
