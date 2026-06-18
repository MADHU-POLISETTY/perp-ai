import BasePage from './BasePage';

export default class HistoryPage extends BasePage {
    get headerTitle() { return '//*[@text="Interview History"]'; }
    get noHistoryText() { return '//*[@text="No interview history found"]'; }
    get historyItem() { return '//*[contains(@text, "Mock Interview") or contains(@text, "%")]'; }
    get deleteButton() { return '//*[@resource-id="btn-delete-log-record" or contains(@content-desc, "delete") or contains(@text, "Delete")]'; }

    async hasHistoryItems(): Promise<boolean> {
        return await this.isDisplayed(this.historyItem);
    }

    async clickHistoryItem() {
        await this.click(this.historyItem);
    }

    async deleteHistoryItem() {
        if (await this.isDisplayed(this.deleteButton)) {
            await this.click(this.deleteButton);
        }
    }
}
