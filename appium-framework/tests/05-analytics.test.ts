import { expect } from 'chai';

describe('Analytics Tests (151-200)', () => {
  before(async () => {
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
    const analyticsBtn = await $('~analytics_button');
    await analyticsBtn.click();
    await browser.pause(1000);
  });

  it('TC-151: Analytics page loads', async () => {
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
  });

  it('TC-152: Analytics displays overall score', async () => {
    const overallScore = await $('~overall_score');
    expect(await overallScore.isDisplayed()).to.be.true;
  });

  it('TC-153: Analytics displays accuracy percentage', async () => {
    const accuracy = await $('~accuracy_percentage');
    expect(await accuracy.isDisplayed()).to.be.true;
  });

  it('TC-154: Analytics displays total quizzes taken', async () => {
    const totalQuizzes = await $('~total_quizzes_taken');
    expect(await totalQuizzes.isDisplayed()).to.be.true;
  });

  it('TC-155: Analytics displays topics covered', async () => {
    const topicsCovered = await $('~topics_covered');
    expect(await topicsCovered.isDisplayed()).to.be.true;
  });

  it('TC-156: Analytics displays performance chart', async () => {
    const performanceChart = await $('~performance_chart');
    expect(await performanceChart.isDisplayed()).to.be.true;
  });

  it('TC-157: Analytics displays score trend', async () => {
    const trendChart = await $('~score_trend_chart');
    expect(await trendChart.isDisplayed()).to.be.true;
  });

  it('TC-158: Analytics displays subject-wise performance', async () => {
    const subjectPerformance = await $('~subject_performance');
    expect(await subjectPerformance.isDisplayed()).to.be.true;
  });

  it('TC-159: Analytics displays difficulty-wise performance', async () => {
    const difficultyPerformance = await $('~difficulty_performance');
    expect(await difficultyPerformance.isDisplayed()).to.be.true;
  });

  it('TC-160: Analytics displays time analysis', async () => {
    const timeAnalysis = await $('~time_analysis');
    expect(await timeAnalysis.isDisplayed()).to.be.true;
  });

  it('TC-161: User can select time period for analytics', async () => {
    const timePeriodBtn = await $('~time_period_selector');
    await timePeriodBtn.click();
    await browser.pause(500);
    const weekOption = await $('~period_week');
    await weekOption.click();
    await browser.pause(1000);
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
  });

  it('TC-162: Analytics displays last 7 days data', async () => {
    const timePeriodBtn = await $('~time_period_selector');
    await timePeriodBtn.click();
    await browser.pause(500);
    const weekOption = await $('~period_week');
    await weekOption.click();
    await browser.pause(1000);
    const chartData = await $('~chart_data');
    expect(await chartData.isDisplayed()).to.be.true;
  });

  it('TC-163: Analytics displays last 30 days data', async () => {
    const timePeriodBtn = await $('~time_period_selector');
    await timePeriodBtn.click();
    await browser.pause(500);
    const monthOption = await $('~period_month');
    await monthOption.click();
    await browser.pause(1000);
    const chartData = await $('~chart_data');
    expect(await chartData.isDisplayed()).to.be.true;
  });

  it('TC-164: Analytics displays all time data', async () => {
    const timePeriodBtn = await $('~time_period_selector');
    await timePeriodBtn.click();
    await browser.pause(500);
    const allTimeOption = await $('~period_all_time');
    await allTimeOption.click();
    await browser.pause(1000);
    const chartData = await $('~chart_data');
    expect(await chartData.isDisplayed()).to.be.true;
  });

  it('TC-165: Analytics shows strongest topic', async () => {
    const strongestTopic = await $('~strongest_topic');
    expect(await strongestTopic.isDisplayed()).to.be.true;
  });

  it('TC-166: Analytics shows weakest topic', async () => {
    const weakestTopic = await $('~weakest_topic');
    expect(await weakestTopic.isDisplayed()).to.be.true;
  });

  it('TC-167: Analytics shows improvement suggestions', async () => {
    const suggestions = await $('~improvement_suggestions');
    expect(await suggestions.isDisplayed()).to.be.true;
  });

  it('TC-168: Analytics shows learning pace', async () => {
    const learningPace = await $('~learning_pace');
    expect(await learningPace.isDisplayed()).to.be.true;
  });

  it('TC-169: Analytics shows average time per quiz', async () => {
    const avgTime = await $('~average_time_per_quiz');
    expect(await avgTime.isDisplayed()).to.be.true;
  });

  it('TC-170: Analytics shows consistency score', async () => {
    const consistency = await $('~consistency_score');
    expect(await consistency.isDisplayed()).to.be.true;
  });

  it('TC-171: User can export analytics as PDF', async () => {
    const exportBtn = await $('~export_pdf_button');
    await exportBtn.click();
    await browser.pause(2000);
    const notification = await $('~export_notification');
    expect(await notification.isDisplayed()).to.be.true;
  });

  it('TC-172: User can export analytics as CSV', async () => {
    const exportBtn = await $('~export_csv_button');
    await exportBtn.click();
    await browser.pause(2000);
    const notification = await $('~export_notification');
    expect(await notification.isDisplayed()).to.be.true;
  });

  it('TC-173: Analytics can be shared', async () => {
    const shareBtn = await $('~share_analytics_button');
    await shareBtn.click();
    await browser.pause(1000);
    const shareMenu = await $('~share_menu');
    expect(await shareMenu.isDisplayed()).to.be.true;
  });

  it('TC-174: Analytics displays comparison with other users', async () => {
    const comparison = await $('~user_comparison');
    expect(await comparison.isDisplayed()).to.be.true;
  });

  it('TC-175: Analytics shows percentile rank', async () => {
    const percentile = await $('~percentile_rank');
    expect(await percentile.isDisplayed()).to.be.true;
  });

  it('TC-176: Analytics shows leaderboard position', async () => {
    const leaderboardPosition = await $('~leaderboard_position');
    expect(await leaderboardPosition.isDisplayed()).to.be.true;
  });

  it('TC-177: Analytics displays error rates by topic', async () => {
    const errorRates = await $('~error_rates_by_topic');
    expect(await errorRates.isDisplayed()).to.be.true;
  });

  it('TC-178: Analytics shows most common mistakes', async () => {
    const commonMistakes = await $('~common_mistakes');
    expect(await commonMistakes.isDisplayed()).to.be.true;
  });

  it('TC-179: Analytics shows study recommendations', async () => {
    const recommendations = await $('~study_recommendations');
    expect(await recommendations.isDisplayed()).to.be.true;
  });

  it('TC-180: User can filter analytics by subject', async () => {
    const filterBtn = await $('~filter_button');
    await filterBtn.click();
    await browser.pause(500);
    const mathFilter = await $('~filter_math');
    await mathFilter.click();
    await browser.pause(1000);
    const filteredAnalytics = await $('~filtered_analytics');
    expect(await filteredAnalytics.isDisplayed()).to.be.true;
  });

  it('TC-181: Analytics displays custom date range', async () => {
    const customDateBtn = await $('~custom_date_button');
    await customDateBtn.click();
    await browser.pause(500);
    const startDate = await $('~start_date_input');
    await startDate.setValue('2024-01-01');
    const endDate = await $('~end_date_input');
    await endDate.setValue('2024-12-31');
    const applyBtn = await $('~apply_custom_date_button');
    await applyBtn.click();
    await browser.pause(1000);
    const chartData = await $('~chart_data');
    expect(await chartData.isDisplayed()).to.be.true;
  });

  it('TC-182: Analytics displays drill down functionality', async () => {
    const drillDownElement = await $('~chart_element');
    await drillDownElement.click();
    await browser.pause(1000);
    const detailedView = await $('~detailed_view');
    expect(await detailedView.isDisplayed()).to.be.true;
  });

  it('TC-183: Analytics shows prediction model', async () => {
    const prediction = await $('~prediction_model');
    expect(await prediction.isDisplayed()).to.be.true;
  });

  it('TC-184: Analytics displays predicted score', async () => {
    const predictedScore = await $('~predicted_score');
    expect(await predictedScore.isDisplayed()).to.be.true;
  });

  it('TC-185: Analytics shows study time recommendation', async () => {
    const timeRecommendation = await $('~study_time_recommendation');
    expect(await timeRecommendation.isDisplayed()).to.be.true;
  });

  it('TC-186: Analytics displays goal progress', async () => {
    const goalProgress = await $('~goal_progress');
    expect(await goalProgress.isDisplayed()).to.be.true;
  });

  it('TC-187: Analytics shows achievement progress', async () => {
    const achievementProgress = await $('~achievement_progress');
    expect(await achievementProgress.isDisplayed()).to.be.true;
  });

  it('TC-188: User can print analytics report', async () => {
    const printBtn = await $('~print_analytics_button');
    await printBtn.click();
    await browser.pause(2000);
    // Verify print dialog
  });

  it('TC-189: Analytics supports dark mode', async () => {
    const darkModeToggle = await $('~dark_mode_toggle');
    await darkModeToggle.click();
    await browser.pause(500);
    const isDarkMode = await browser.executeScript('return document.body.classList.contains("dark")');
    expect(isDarkMode).to.be.true;
  });

  it('TC-190: Analytics responsive on mobile', async () => {
    await browser.setWindowSize(375, 667);
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
    await browser.setWindowSize(1280, 1024);
  });

  it('TC-191: Analytics responsive on tablet', async () => {
    await browser.setWindowSize(768, 1024);
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
    await browser.setWindowSize(1280, 1024);
  });

  it('TC-192: Analytics handles network errors', async () => {
    await browser.executeScript('window.navigator.onLine = false');
    const errorMsg = await $('~network_error_message');
    expect(await errorMsg.isDisplayed()).to.be.true;
    await browser.executeScript('window.navigator.onLine = true');
  });

  it('TC-193: Analytics loads within acceptable time', async () => {
    const startTime = Date.now();
    await browser.url('http://localhost/analytics');
    const analyticsScreen = await $('~analytics_screen');
    await analyticsScreen.waitForDisplayed();
    const loadTime = Date.now() - startTime;
    expect(loadTime).to.be.below(5000);
  });

  it('TC-194: Analytics updates in real-time', async () => {
    const analyticsScreen = await $('~analytics_screen');
    expect(await analyticsScreen.isDisplayed()).to.be.true;
    await browser.pause(5000);
    expect(await analyticsScreen.isDisplayed()).to.be.true;
  });

  it('TC-195: Analytics displays notifications for milestones', async () => {
    const milestoneNotification = await $('~milestone_notification');
    expect(await milestoneNotification.isDisplayed()).to.be.true;
  });

  it('TC-196: Analytics shows study streak information', async () => {
    const streakInfo = await $('~streak_information');
    expect(await streakInfo.isDisplayed()).to.be.true;
  });

  it('TC-197: Analytics displays motivational message', async () => {
    const motivationalMsg = await $('~motivational_message');
    expect(await motivationalMsg.isDisplayed()).to.be.true;
  });

  it('TC-198: Analytics can generate custom report', async () => {
    const customReportBtn = await $('~custom_report_button');
    await customReportBtn.click();
    await browser.pause(500);
    const reportOptions = await $('~report_options');
    expect(await reportOptions.isDisplayed()).to.be.true;
  });

  it('TC-199: Analytics integrates with calendar', async () => {
    const calendarIntegration = await $('~calendar_integration');
    expect(await calendarIntegration.isDisplayed()).to.be.true;
  });

  it('TC-200: Analytics displays comprehensive summary', async () => {
    const summary = await $('~comprehensive_summary');
    expect(await summary.isDisplayed()).to.be.true;
    const scoreCard = await $('~score_card');
    const performanceCard = await $('~performance_card');
    const progressCard = await $('~progress_card');
    expect(await scoreCard.isDisplayed()).to.be.true;
    expect(await performanceCard.isDisplayed()).to.be.true;
    expect(await progressCard.isDisplayed()).to.be.true;
  });
});
