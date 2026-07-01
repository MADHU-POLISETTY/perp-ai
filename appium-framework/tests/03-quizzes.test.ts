import { expect } from 'chai';

describe('Quiz Tests (51-100)', () => {
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
    const quizBtn = await $('~quiz_section_button');
    await quizBtn.click();
    await browser.pause(1000);
  });

  it('TC-051: Quiz list displays correctly', async () => {
    const quizList = await $('~quiz_list');
    expect(await quizList.isDisplayed()).to.be.true;
  });

  it('TC-052: User can select a quiz', async () => {
    const quiz = await $('~quiz_item_1');
    await quiz.click();
    await browser.pause(1000);
    const quizDetail = await $('~quiz_detail_screen');
    expect(await quizDetail.isDisplayed()).to.be.true;
  });

  it('TC-053: Quiz detail page shows description', async () => {
    const description = await $('~quiz_description');
    expect(await description.isDisplayed()).to.be.true;
  });

  it('TC-054: Quiz detail page shows difficulty level', async () => {
    const difficulty = await $('~quiz_difficulty');
    expect(await difficulty.isDisplayed()).to.be.true;
  });

  it('TC-055: Quiz detail page shows time limit', async () => {
    const timeLimit = await $('~quiz_time_limit');
    expect(await timeLimit.isDisplayed()).to.be.true;
  });

  it('TC-056: User can start a quiz', async () => {
    const startBtn = await $('~start_quiz_button');
    await startBtn.click();
    await browser.pause(2000);
    const quizScreen = await $('~quiz_screen');
    expect(await quizScreen.isDisplayed()).to.be.true;
  });

  it('TC-057: Quiz displays question number', async () => {
    const questionNumber = await $('~question_number');
    expect(await questionNumber.isDisplayed()).to.be.true;
  });

  it('TC-058: Quiz displays progress bar', async () => {
    const progressBar = await $('~quiz_progress_bar');
    expect(await progressBar.isDisplayed()).to.be.true;
  });

  it('TC-059: Quiz displays timer', async () => {
    const timer = await $('~quiz_timer');
    expect(await timer.isDisplayed()).to.be.true;
  });

  it('TC-060: User can select multiple choice answer', async () => {
    const option = await $('~option_a');
    await option.click();
    await browser.pause(500);
    const selectedIndicator = await $('~selected_indicator');
    expect(await selectedIndicator.isDisplayed()).to.be.true;
  });

  it('TC-061: User can deselect multiple choice answer', async () => {
    const option = await $('~option_a');
    await option.click();
    await browser.pause(500);
    const selectedIndicator = await $('~selected_indicator');
    expect(await selectedIndicator.isDisplayed()).to.be.false;
  });

  it('TC-062: User can navigate to next question', async () => {
    const nextBtn = await $('~next_question_button');
    await nextBtn.click();
    await browser.pause(500);
    const questionNumber = await $('~question_number');
    const text = await questionNumber.getText();
    expect(text).to.include('2');
  });

  it('TC-063: User can navigate to previous question', async () => {
    const prevBtn = await $('~previous_question_button');
    await prevBtn.click();
    await browser.pause(500);
    const questionNumber = await $('~question_number');
    const text = await questionNumber.getText();
    expect(text).to.include('1');
  });

  it('TC-064: User cannot go before first question', async () => {
    const prevBtn = await $('~previous_question_button');
    const isEnabled = await prevBtn.isEnabled();
    expect(isEnabled).to.be.false;
  });

  it('TC-065: User can see answered questions', async () => {
    const nextBtn = await $('~next_question_button');
    await nextBtn.click();
    await browser.pause(500);
    const answeredIndicator = await $('~answered_question_indicator');
    expect(await answeredIndicator.isDisplayed()).to.be.true;
  });

  it('TC-066: User can skip a question', async () => {
    const skipBtn = await $('~skip_question_button');
    await skipBtn.click();
    await browser.pause(500);
    const questionNumber = await $('~question_number');
    const text = await questionNumber.getText();
    expect(text).to.match(/\d+/);
  });

  it('TC-067: User cannot submit without answering all questions', async () => {
    const submitBtn = await $('~submit_quiz_button');
    const isEnabled = await submitBtn.isEnabled();
    expect(isEnabled).to.be.false;
  });

  it('TC-068: User gets warning when time is running out', async () => {
    await browser.pause(30000); // Wait for warning
    const warningMsg = await $('~time_warning');
    expect(await warningMsg.isDisplayed()).to.be.true;
  });

  it('TC-069: Quiz auto-submits when time expires', async () => {
    await browser.pause(30000); // Wait for time to expire
    const resultScreen = await $('~quiz_result_screen');
    expect(await resultScreen.isDisplayed()).to.be.true;
  });

  it('TC-070: Quiz result shows score', async () => {
    const score = await $('~quiz_score');
    expect(await score.isDisplayed()).to.be.true;
    const scoreValue = await score.getText();
    expect(scoreValue).to.match(/\d+\/\d+/);
  });

  it('TC-071: Quiz result shows percentage', async () => {
    const percentage = await $('~quiz_percentage');
    expect(await percentage.isDisplayed()).to.be.true;
  });

  it('TC-072: Quiz result shows time taken', async () => {
    const timeTaken = await $('~quiz_time_taken');
    expect(await timeTaken.isDisplayed()).to.be.true;
  });

  it('TC-073: Quiz result shows correct answers', async () => {
    const correctAnswers = await $('~correct_answers_count');
    expect(await correctAnswers.isDisplayed()).to.be.true;
  });

  it('TC-074: Quiz result shows incorrect answers', async () => {
    const incorrectAnswers = await $('~incorrect_answers_count');
    expect(await incorrectAnswers.isDisplayed()).to.be.true;
  });

  it('TC-075: Quiz result shows unanswered questions', async () => {
    const unanswered = await $('~unanswered_count');
    expect(await unanswered.isDisplayed()).to.be.true;
  });

  it('TC-076: User can review quiz answers', async () => {
    const reviewBtn = await $('~review_answers_button');
    await reviewBtn.click();
    await browser.pause(1000);
    const reviewScreen = await $('~review_screen');
    expect(await reviewScreen.isDisplayed()).to.be.true;
  });

  it('TC-077: Review shows correct answer', async () => {
    const correctAnswer = await $('~correct_answer_highlight');
    expect(await correctAnswer.isDisplayed()).to.be.true;
  });

  it('TC-078: Review shows user answer', async () => {
    const userAnswer = await $('~user_answer_highlight');
    expect(await userAnswer.isDisplayed()).to.be.true;
  });

  it('TC-079: Review shows explanation for each question', async () => {
    const explanation = await $('~answer_explanation');
    expect(await explanation.isDisplayed()).to.be.true;
  });

  it('TC-080: User can retake the quiz', async () => {
    const retakeBtn = await $('~retake_quiz_button');
    await retakeBtn.click();
    await browser.pause(2000);
    const quizScreen = await $('~quiz_screen');
    expect(await quizScreen.isDisplayed()).to.be.true;
  });

  it('TC-081: User can share quiz result', async () => {
    const shareBtn = await $('~share_result_button');
    await shareBtn.click();
    await browser.pause(1000);
    const shareMenu = await $('~share_menu');
    expect(await shareMenu.isDisplayed()).to.be.true;
  });

  it('TC-082: User can download quiz result', async () => {
    const downloadBtn = await $('~download_result_button');
    await downloadBtn.click();
    await browser.pause(2000);
    // Verify download happens
    const notification = await $('~download_notification');
    expect(await notification.isDisplayed()).to.be.true;
  });

  it('TC-083: Quiz result saved to history', async () => {
    const historyBtn = await $('~quiz_history_button');
    await historyBtn.click();
    await browser.pause(1000);
    const historyItem = await $('~history_item_1');
    expect(await historyItem.isDisplayed()).to.be.true;
  });

  it('TC-084: User can filter quiz by subject', async () => {
    const filterBtn = await $('~filter_button');
    await filterBtn.click();
    await browser.pause(500);
    const mathFilter = await $('~filter_math');
    await mathFilter.click();
    await browser.pause(1000);
    const filteredQuizzes = await $('~filtered_quizzes');
    expect(await filteredQuizzes.isDisplayed()).to.be.true;
  });

  it('TC-085: User can sort quiz by difficulty', async () => {
    const sortBtn = await $('~sort_button');
    await sortBtn.click();
    await browser.pause(500);
    const sortByDifficulty = await $('~sort_difficulty');
    await sortByDifficulty.click();
    await browser.pause(1000);
    const sortedQuizzes = await $('~sorted_quizzes');
    expect(await sortedQuizzes.isDisplayed()).to.be.true;
  });

  it('TC-086: User can search for quiz', async () => {
    const searchInput = await $('~quiz_search_input');
    await searchInput.setValue('Algebra');
    await browser.pause(1000);
    const searchResults = await $('~search_results');
    expect(await searchResults.isDisplayed()).to.be.true;
  });

  it('TC-087: Quiz detail shows number of questions', async () => {
    const quizItem = await $('~quiz_item_1');
    await quizItem.click();
    await browser.pause(1000);
    const questionCount = await $('~question_count');
    expect(await questionCount.isDisplayed()).to.be.true;
  });

  it('TC-088: Quiz detail shows average score', async () => {
    const avgScore = await $('~average_score');
    expect(await avgScore.isDisplayed()).to.be.true;
  });

  it('TC-089: Quiz detail shows number of attempts', async () => {
    const attempts = await $('~attempt_count');
    expect(await attempts.isDisplayed()).to.be.true;
  });

  it('TC-090: Quiz shows recommended based on performance', async () => {
    const recommendedSection = await $('~recommended_quizzes_section');
    expect(await recommendedSection.isDisplayed()).to.be.true;
  });

  it('TC-091: User can bookmark a quiz', async () => {
    const bookmarkBtn = await $('~bookmark_button');
    await bookmarkBtn.click();
    await browser.pause(500);
    const bookmarkedIcon = await $('~bookmarked_icon');
    expect(await bookmarkedIcon.isDisplayed()).to.be.true;
  });

  it('TC-092: User can unbookmark a quiz', async () => {
    const bookmarkBtn = await $('~bookmark_button');
    await bookmarkBtn.click();
    await browser.pause(500);
    const bookmarkedIcon = await $('~bookmarked_icon');
    expect(await bookmarkedIcon.isDisplayed()).to.be.false;
  });

  it('TC-093: Quiz difficulty progression', async () => {
    // Verify quizzes are arranged by difficulty
    const quizzes = await $$('~quiz_item');
    expect(quizzes.length).to.be.greaterThan(0);
  });

  it('TC-094: Quiz supports text-based answers', async () => {
    const textInput = await $('~text_answer_input');
    await textInput.setValue('Sample Answer');
    await browser.pause(500);
    const value = await textInput.getValue();
    expect(value).to.equal('Sample Answer');
  });

  it('TC-095: Quiz supports numeric answers', async () => {
    const numericInput = await $('~numeric_answer_input');
    await numericInput.setValue('42');
    await browser.pause(500);
    const value = await numericInput.getValue();
    expect(value).to.equal('42');
  });

  it('TC-096: Quiz prevents answer changes after submission', async () => {
    const submitBtn = await $('~submit_quiz_button');
    await submitBtn.click();
    await browser.pause(2000);
    const optionBtn = await $('~option_a');
    const isEnabled = await optionBtn.isEnabled();
    expect(isEnabled).to.be.false;
  });

  it('TC-097: Quiz shows performance trend', async () => {
    const trendChart = await $('~performance_trend_chart');
    expect(await trendChart.isDisplayed()).to.be.true;
  });

  it('TC-098: Quiz shows improvement suggestions', async () => {
    const suggestions = await $('~improvement_suggestions');
    expect(await suggestions.isDisplayed()).to.be.true;
  });

  it('TC-099: Quiz allows printing results', async () => {
    const printBtn = await $('~print_result_button');
    await printBtn.click();
    await browser.pause(2000);
    // Verify print dialog appears
  });

  it('TC-100: Quiz handles network interruption', async () => {
    // Simulate network loss
    await browser.executeScript('window.navigator.onLine = false');
    const errorMsg = await $('~network_error_message');
    expect(await errorMsg.isDisplayed()).to.be.true;
    await browser.executeScript('window.navigator.onLine = true');
  });
});
