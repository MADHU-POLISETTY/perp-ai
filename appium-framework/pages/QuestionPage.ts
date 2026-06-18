import BasePage from './BasePage';

export default class QuestionPage extends BasePage {
    get questionText() { return '//android.widget.TextView[contains(@text, "?") or @resource-id="question-display-block"]'; }
    get answerInput() { return '//android.widget.EditText[contains(@text, "Answer") or contains(@hint, "answer") or @resource-id="textarea-answer-box"]'; }
    get timerText() { return '//*[@resource-id="timer-display" or contains(@text, ":")]'; }
    get progressIndicator() { return '//android.widget.ProgressBar or @resource-id="question-progress-indicator"'; }
    
    // Navigation controls
    get prevButton() { return '//*[@text="Previous" or @resource-id="btn-prev-question"]'; }
    get nextButton() { return '//*[@text="Next" or @resource-id="btn-next-question"]'; }
    get submitButton() { return '//*[@text="Submit" or @resource-id="btn-submit-interview"]'; }

    // MCQ option item template selector
    optionItem(text: string) { return `//android.widget.TextView[@text="${text}"]`; }

    async answerQuestionWithText(answer: string) {
        await this.enterText(this.answerInput, answer);
        await this.hideKeyboard();
    }

    async selectOption(optionText: string) {
        const selector = this.optionItem(optionText);
        await this.click(selector);
    }

    async clickNext() {
        if (await this.isDisplayed(this.nextButton)) {
            await this.click(this.nextButton);
        } else {
            await this.click(this.submitButton);
        }
    }

    async clickPrevious() {
        await this.click(this.prevButton);
    }

    async submitInterview() {
        await this.click(this.submitButton);
    }

    async getRemainingTime(): Promise<string> {
        return await this.getText(this.timerText);
    }
}
