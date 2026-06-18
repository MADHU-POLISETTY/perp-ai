import BasePage from './BasePage';

export default class InterviewCategoryPage extends BasePage {
    get title() { return '//*[@text="Technical Interview"]'; }
    get backButton() { return '//*[@content-desc="Back"]'; }
    
    // Domain categories selectors
    get javaDomain() { return '//*[@text="Java"]'; }
    get pythonDomain() { return '//*[@text="Python"]'; }
    get cppDomain() { return '//*[@text="C++"]'; }
    get awsDomain() { return '//*[@text="AWS"]'; }
    get devopsDomain() { return '//*[@text="DevOps"]'; }
    get sqlDomain() { return '//*[@text="SQL"]'; }
    get mlDomain() { return '//*[@text="ML"]'; }
    get systemDesignDomain() { return '//*[@text="System Design"]'; }

    async selectJava() { await this.click(this.javaDomain); }
    async selectPython() { await this.click(this.pythonDomain); }
    async selectAWS() { await this.click(this.awsDomain); }
    async selectSystemDesign() { await this.click(this.systemDesignDomain); }
}
