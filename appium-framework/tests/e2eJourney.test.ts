import LoginPage from '../pages/LoginPage';
import RegistrationPage from '../pages/RegistrationPage';
import ForgotPasswordPage from '../pages/ForgotPasswordPage';
import DashboardPage from '../pages/DashboardPage';
import InterviewCategoryPage from '../pages/InterviewCategoryPage';
import InterviewSessionPage from '../pages/InterviewSessionPage';
import QuestionPage from '../pages/QuestionPage';
import VoiceRecordingPage from '../pages/VoiceRecordingPage';
import AIFeedbackPage from '../pages/AIFeedbackPage';
import AnalyticsPage from '../pages/AnalyticsPage';
import HistoryPage from '../pages/HistoryPage';
import ProfilePage from '../pages/ProfilePage';
import SettingsPage from '../pages/SettingsPage';
import { browser } from '@wdio/globals';

describe('Prep-AI Mobile E2E Automation Testing Suite', () => {
    const loginPage = new LoginPage();
    const registrationPage = new RegistrationPage();
    const forgotPasswordPage = new ForgotPasswordPage();
    const dashboardPage = new DashboardPage();
    const categoryPage = new InterviewCategoryPage();
    const sessionPage = new InterviewSessionPage();
    const questionPage = new QuestionPage();
    const voicePage = new VoiceRecordingPage();
    const feedbackPage = new AIFeedbackPage();
    const analyticsPage = new AnalyticsPage();
    const historyPage = new HistoryPage();
    const profilePage = new ProfilePage();
    const settingsPage = new SettingsPage();

    before(async () => {
        console.log('Initializing Prep-AI Mobile E2E Assessment Tests...');
    });

    it('TS-001: Register a new candidate account successfully', async () => {
        await loginPage.clickRegister();
        await registrationPage.register('Manoj Kumar', 'manoj.qa@example.com', 'SecurePass123!');
        
        // Wait and assert redirection to Login or Home screen
        const isLoginVisible = await loginPage.isDisplayed(loginPage.loginButton);
        expect(isLoginVisible).toBe(true);
    });

    it('TS-002: Login with invalid credentials displays error message', async () => {
        await loginPage.login('invalid@example.com', 'wrongpassword');
        const isErrorVisible = await loginPage.isDisplayed(loginPage.errorText);
        expect(isErrorVisible).toBe(true);
    });

    it('TS-003: Forgot password reset flow send reset link', async () => {
        await loginPage.clickForgotPassword();
        await forgotPasswordPage.resetPassword('manoj.qa@example.com');
        const isSuccessVisible = await forgotPasswordPage.isDisplayed(forgotPasswordPage.successMessage);
        expect(isSuccessVisible).toBe(true);
        // Back to login page
        await browser.back();
    });

    it('TS-004: Login with valid credentials successfully', async () => {
        await loginPage.login('manoj.qa@example.com', 'SecurePass123!');
        const isHomeSearchVisible = await dashboardPage.isDisplayed(dashboardPage.searchInput);
        expect(isHomeSearchVisible).toBe(true);
    });

    it('TS-005: Navigate category and start interview session', async () => {
        await dashboardPage.clickCategoryTechnical();
        await categoryPage.selectJava();
        
        await sessionPage.configureRole('Java Backend Engineer');
        await sessionPage.startSession();
        
        const isQuestionLoaded = await questionPage.isDisplayed(questionPage.questionText);
        expect(isQuestionLoaded).toBe(true);
    });

    it('TS-006: Execute interview questions flow and record voice', async () => {
        // Answer Question 1
        const questionText1 = await questionPage.getText(questionPage.questionText);
        console.log(`Question 1 text: ${questionText1}`);
        
        await voicePage.startRecording();
        await browser.pause(2000); // Simulate audio streaming
        await voicePage.stopRecording();
        
        await questionPage.answerQuestionWithText('Java uses JVM to run compiled bytecode which makes it platform independent.');
        await questionPage.clickNext();

        // Answer Question 2
        const questionText2 = await questionPage.getText(questionPage.questionText);
        console.log(`Question 2 text: ${questionText2}`);
        await questionPage.answerQuestionWithText('We manage transaction rollback and isolation properties using Spring @Transactional annotations.');
        await questionPage.submitInterview();
    });

    it('TS-007: Validate AI evaluation results and score metrics', async () => {
        const isFeedbackHeaderLoaded = await feedbackPage.isDisplayed(feedbackPage.performanceTitle);
        expect(isFeedbackHeaderLoaded).toBe(true);
        
        const overallScore = await feedbackPage.getScore();
        console.log(`Overall score percentage: ${overallScore}`);
        expect(overallScore).toContain('%');

        const strongAreas = await feedbackPage.isDisplayed(feedbackPage.strongAreasSection);
        const improvementAreas = await feedbackPage.isDisplayed(feedbackPage.improvementAreasSection);
        expect(strongAreas).toBe(true);
        expect(improvementAreas).toBe(true);
        
        await feedbackPage.clickBackToHome();
    });

    it('TS-008: View stats and performance analytics graphs', async () => {
        await dashboardPage.navigateToStats();
        const loaded = await analyticsPage.isAnalyticsLoaded();
        expect(loaded).toBe(true);
    });

    it('TS-009: Inspect previous interview logs in history', async () => {
        await dashboardPage.navigateToHome();
        await dashboardPage.clickFeatureHistory();
        
        const hasHistory = await historyPage.hasHistoryItems();
        expect(hasHistory).toBe(true);
        
        await browser.back();
    });

    it('TS-010: Toggle settings theme configuration', async () => {
        await dashboardPage.navigateToProfile();
        // Since settings screen is sub-screen or part of profile, toggle dark theme
        await settingsPage.toggleDarkMode();
        await settingsPage.toggleDarkMode(); // Toggle back
    });

    it('TS-011: Sign out from application successfully', async () => {
        await profilePage.logoutViaButton();
        const isLoginBtnDisplayed = await loginPage.isDisplayed(loginPage.loginButton);
        expect(isLoginBtnDisplayed).toBe(true);
    });
});
