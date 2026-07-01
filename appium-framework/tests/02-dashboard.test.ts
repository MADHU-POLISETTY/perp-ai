import { expect } from 'chai';

describe('Dashboard Tests (21-50)', () => {
  before(async () => {
    // Login before each test suite
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const emailInput = await $('~email_input');
    await emailInput.setValue('testuser@example.com');
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('validPassword123');
    const submitBtn = await $('~login_submit');
    await submitBtn.click();
    await browser.pause(2000);
  });

  it('TC-021: Dashboard loads successfully', async () => {
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  it('TC-022: Dashboard displays user name', async () => {
    const userName = await $('~dashboard_user_name');
    expect(await userName.getText()).to.include('Test User');
  });

  it('TC-023: Dashboard displays preparation progress', async () => {
    const progressBar = await $('~progress_bar');
    expect(await progressBar.isDisplayed()).to.be.true;
  });

  it('TC-024: Dashboard displays upcoming quizzes', async () => {
    const quizList = await $('~quiz_list');
    expect(await quizList.isDisplayed()).to.be.true;
  });

  it('TC-025: Dashboard displays recent activity', async () => {
    const activityList = await $('~activity_list');
    expect(await activityList.isDisplayed()).to.be.true;
  });

  it('TC-026: User can navigate to quiz section', async () => {
    const quizBtn = await $('~quiz_section_button');
    await quizBtn.click();
    await browser.pause(1000);
    const quizScreen = await $('~quiz_screen');
    expect(await quizScreen.isDisplayed()).to.be.true;
  });

  it('TC-027: User can navigate to study materials', async () => {
    await browser.url('http://localhost/dashboard');
    const studyBtn = await $('~study_materials_button');
    await studyBtn.click();
    await browser.pause(1000);
    const studyScreen = await $('~study_materials_screen');
    expect(await studyScreen.isDisplayed()).to.be.true;
  });

  it('TC-028: User can navigate to performance analytics', async () => {
    await browser.url('http://localhost/dashboard');
    const analyticsBtn = await $('~analytics_button');
    await analyticsBtn.click();
    await browser.pause(1000);
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
  });

  it('TC-029: Dashboard displays statistics card', async () => {
    const statsCard = await $('~statistics_card');
    expect(await statsCard.isDisplayed()).to.be.true;
    const totalQuizzes = await $('~total_quizzes_stat');
    expect(await totalQuizzes.getText()).to.match(/\d+/);
  });

  it('TC-030: User can refresh dashboard', async () => {
    const refreshBtn = await $('~refresh_button');
    await refreshBtn.click();
    await browser.pause(2000);
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  it('TC-031: Search functionality works on dashboard', async () => {
    const searchInput = await $('~search_input');
    await searchInput.setValue('math');
    await browser.pause(1000);
    const searchResults = await $('~search_results');
    expect(await searchResults.isDisplayed()).to.be.true;
  });

  it('TC-032: Filter by subject works', async () => {
    const filterBtn = await $('~filter_button');
    await filterBtn.click();
    await browser.pause(500);
    const mathFilter = await $('~filter_math');
    await mathFilter.click();
    await browser.pause(1000);
    const filteredResults = await $('~filtered_results');
    expect(await filteredResults.isDisplayed()).to.be.true;
  });

  it('TC-033: Sort by difficulty works', async () => {
    const sortBtn = await $('~sort_button');
    await sortBtn.click();
    await browser.pause(500);
    const sortByDifficulty = await $('~sort_difficulty');
    await sortByDifficulty.click();
    await browser.pause(1000);
    const sortedResults = await $('~sorted_results');
    expect(await sortedResults.isDisplayed()).to.be.true;
  });

  it('TC-034: Dashboard notifications display correctly', async () => {
    const notificationBell = await $('~notification_bell');
    await notificationBell.click();
    await browser.pause(500);
    const notificationList = await $('~notification_list');
    expect(await notificationList.isDisplayed()).to.be.true;
  });

  it('TC-035: User can mark notification as read', async () => {
    const notificationBell = await $('~notification_bell');
    await notificationBell.click();
    await browser.pause(500);
    const firstNotification = await $('~notification_item_1');
    await firstNotification.click();
    await browser.pause(500);
    const readIcon = await $('~notification_read_icon');
    expect(await readIcon.isDisplayed()).to.be.true;
  });

  it('TC-036: Dashboard displays recommended quizzes', async () => {
    const recommendedSection = await $('~recommended_section');
    expect(await recommendedSection.isDisplayed()).to.be.true;
  });

  it('TC-037: User can start recommended quiz', async () => {
    const recommendedQuiz = await $('~recommended_quiz_1');
    await recommendedQuiz.click();
    await browser.pause(2000);
    const quizScreen = await $('~quiz_screen');
    expect(await quizScreen.isDisplayed()).to.be.true;
  });

  it('TC-038: Dashboard displays streak counter', async () => {
    const streakCounter = await $('~streak_counter');
    expect(await streakCounter.isDisplayed()).to.be.true;
  });

  it('TC-039: User can view streak details', async () => {
    const streakCounter = await $('~streak_counter');
    await streakCounter.click();
    await browser.pause(1000);
    const streakDetails = await $('~streak_details');
    expect(await streakDetails.isDisplayed()).to.be.true;
  });

  it('TC-040: Dashboard displays learning goals', async () => {
    const goalsSection = await $('~goals_section');
    expect(await goalsSection.isDisplayed()).to.be.true;
  });

  it('TC-041: User can add new learning goal', async () => {
    const addGoalBtn = await $('~add_goal_button');
    await addGoalBtn.click();
    await browser.pause(500);
    const goalInput = await $('~goal_input');
    await goalInput.setValue('Complete 50 quizzes this month');
    const saveBtn = await $('~save_goal_button');
    await saveBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-042: User can edit learning goal', async () => {
    const editGoalBtn = await $('~edit_goal_button_1');
    await editGoalBtn.click();
    await browser.pause(500);
    const goalInput = await $('~goal_input');
    await goalInput.clearValue();
    await goalInput.setValue('Complete 75 quizzes this month');
    const saveBtn = await $('~save_goal_button');
    await saveBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-043: User can delete learning goal', async () => {
    const deleteGoalBtn = await $('~delete_goal_button_1');
    await deleteGoalBtn.click();
    await browser.pause(500);
    const confirmBtn = await $('~confirm_delete_button');
    await confirmBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.getText()).to.include('Goal deleted');
  });

  it('TC-044: Dashboard responsive on mobile view', async () => {
    await browser.setWindowSize(375, 667);
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
    await browser.setWindowSize(1280, 1024);
  });

  it('TC-045: Dashboard responsive on tablet view', async () => {
    await browser.setWindowSize(768, 1024);
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
    await browser.setWindowSize(1280, 1024);
  });

  it('TC-046: Dashboard loads within acceptable time', async () => {
    const startTime = Date.now();
    await browser.url('http://localhost/dashboard');
    await browser.pause(1000);
    const dashboard = await $('~dashboard_screen');
    await dashboard.waitForDisplayed();
    const loadTime = Date.now() - startTime;
    expect(loadTime).to.be.below(5000);
  });

  it('TC-047: Dashboard handles network errors gracefully', async () => {
    // Simulate network error
    await browser.executeScript('window.navigator.onLine = false');
    const errorMsg = await $('~error_message');
    expect(await errorMsg.isDisplayed()).to.be.true;
    await browser.executeScript('window.navigator.onLine = true');
  });

  it('TC-048: User preferences are saved', async () => {
    const preferenceBtn = await $('~preferences_button');
    await preferenceBtn.click();
    await browser.pause(500);
    const darkModeToggle = await $('~dark_mode_toggle');
    await darkModeToggle.click();
    await browser.pause(500);
    const saveBtn = await $('~save_preferences_button');
    await saveBtn.click();
    await browser.pause(1000);
    await browser.refresh();
    const isDarkMode = await browser.executeScript('return document.body.classList.contains("dark")');
    expect(isDarkMode).to.be.true;
  });

  it('TC-049: Dashboard displays achievement badges', async () => {
    const achievementSection = await $('~achievement_section');
    expect(await achievementSection.isDisplayed()).to.be.true;
  });

  it('TC-050: User can view achievement details', async () => {
    const achievementBadge = await $('~achievement_badge_1');
    await achievementBadge.click();
    await browser.pause(1000);
    const achievementDetails = await $('~achievement_details');
    expect(await achievementDetails.isDisplayed()).to.be.true;
  });
});
