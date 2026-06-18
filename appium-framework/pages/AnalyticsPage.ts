import BasePage from './BasePage';

export default class AnalyticsPage extends BasePage {
    get headerTitle() { return '//*[contains(@text, "Analytics") or contains(@text, "Stats") or contains(@text, "Dashboard")]'; }
    get averageScoreCard() { return '//*[contains(@text, "Average Score") or contains(@text, "Avg Score")]'; }
    get practicesCompletedCard() { return '//*[contains(@text, "Practices") or contains(@text, "Completed")]'; }
    get trendGraph() { return '//android.view.View[contains(@content-desc, "chart") or contains(@resource-id, "chart") or contains(@resource-id, "recharts")]'; }

    async isAnalyticsLoaded(): Promise<boolean> {
        return await this.isDisplayed(this.headerTitle);
    }
}
