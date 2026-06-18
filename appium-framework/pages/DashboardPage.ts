import BasePage from './BasePage';

export default class DashboardPage extends BasePage {
    get searchInput() { return '//android.widget.EditText[contains(@text, "Search") or contains(@hint, "Search")]'; }
    get profileIcon() { return '//*[@content-desc="Profile"]'; }
    
    // Bottom Navigation selectors
    get homeTab() { return '//*[@text="Home" or @content-desc="Home"]'; }
    get statsTab() { return '//*[@text="Stats" or @content-desc="Stats"]'; }
    get alertsTab() { return '//*[@text="Alerts" or @content-desc="Alerts"]'; }
    get profileTab() { return '//*[@text="Profile" or @content-desc="Profile"]'; }

    // Categories
    get hrCategory() { return '//*[@text="HR Interview"]'; }
    get technicalCategory() { return '//*[@text="Technical"]'; }
    get aptitudeCategory() { return '//*[@text="Aptitude"]'; }
    get aiMockCategory() { return '//*[@text="AI Mock"]'; }
    get codingCategory() { return '//*[@text="Coding"]'; }
    get gdPrepCategory() { return '//*[@text="GD Prep"]'; }

    // Key Features
    get practiceModeFeature() { return '//*[@text="Practice Mode"]'; }
    get analyzeResultsFeature() { return '//*[@text="Analyze Results"]'; }
    get resumeAnalyzerFeature() { return '//*[@text="Resume Analyzer"]'; }
    get historyFeature() { return '//*[@text="History"]'; }

    async searchTopic(topic: string) {
        await this.enterText(this.searchInput, topic);
        await this.hideKeyboard();
    }

    async navigateToHome() { await this.click(this.homeTab); }
    async navigateToStats() { await this.click(this.statsTab); }
    async navigateToAlerts() { await this.click(this.alertsTab); }
    async navigateToProfile() { await this.click(this.profileTab); }

    async clickCategoryHR() { await this.click(this.hrCategory); }
    async clickCategoryTechnical() { await this.click(this.technicalCategory); }
    async clickCategoryAptitude() { await this.click(this.aptitudeCategory); }
    async clickCategoryCoding() { await this.click(this.codingCategory); }

    async clickFeatureResumeAnalyzer() { await this.click(this.resumeAnalyzerFeature); }
    async clickFeatureHistory() { await this.click(this.historyFeature); }
}
