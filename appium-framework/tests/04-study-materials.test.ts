import { expect } from 'chai';

describe('Study Materials Tests (101-150)', () => {
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
    const studyBtn = await $('~study_materials_button');
    await studyBtn.click();
    await browser.pause(1000);
  });

  it('TC-101: Study materials page loads', async () => {
    const studyScreen = await $('~study_materials_screen');
    expect(await studyScreen.isDisplayed()).to.be.true;
  });

  it('TC-102: Study materials list displays', async () => {
    const materialsList = await $('~materials_list');
    expect(await materialsList.isDisplayed()).to.be.true;
  });

  it('TC-103: Study material has title', async () => {
    const materialTitle = await $('~material_title');
    expect(await materialTitle.isDisplayed()).to.be.true;
  });

  it('TC-104: Study material has description', async () => {
    const description = await $('~material_description');
    expect(await description.isDisplayed()).to.be.true;
  });

  it('TC-105: Study material has difficulty level', async () => {
    const difficulty = await $('~material_difficulty');
    expect(await difficulty.isDisplayed()).to.be.true;
  });

  it('TC-106: User can open study material', async () => {
    const material = await $('~material_item_1');
    await material.click();
    await browser.pause(2000);
    const materialContent = await $('~material_content');
    expect(await materialContent.isDisplayed()).to.be.true;
  });

  it('TC-107: Study material displays content', async () => {
    const content = await $('~material_content_text');
    expect(await content.isDisplayed()).to.be.true;
  });

  it('TC-108: Study material shows topic', async () => {
    const topic = await $('~material_topic');
    expect(await topic.isDisplayed()).to.be.true;
  });

  it('TC-109: Study material shows subject', async () => {
    const subject = await $('~material_subject');
    expect(await subject.isDisplayed()).to.be.true;
  });

  it('TC-110: Study material shows author', async () => {
    const author = await $('~material_author');
    expect(await author.isDisplayed()).to.be.true;
  });

  it('TC-111: User can scroll through study material', async () => {
    const content = await $('~material_content');
    await content.scroll({ x: 0, y: 500 });
    await browser.pause(500);
    const scrolledContent = await $('~material_content');
    expect(await scrolledContent.isDisplayed()).to.be.true;
  });

  it('TC-112: Study material supports highlighting', async () => {
    const text = await $('~material_paragraph');
    await browser.executeScript('window.getSelection().addRange(document.createRange())');
    await browser.pause(500);
    const highlightBtn = await $('~highlight_button');
    expect(await highlightBtn.isDisplayed()).to.be.true;
  });

  it('TC-113: User can bookmark study material', async () => {
    const bookmarkBtn = await $('~bookmark_material_button');
    await bookmarkBtn.click();
    await browser.pause(500);
    const bookmarkedIcon = await $('~bookmarked_icon');
    expect(await bookmarkedIcon.isDisplayed()).to.be.true;
  });

  it('TC-114: User can unbookmark study material', async () => {
    const bookmarkBtn = await $('~bookmark_material_button');
    await bookmarkBtn.click();
    await browser.pause(500);
    const bookmarkedIcon = await $('~bookmarked_icon');
    expect(await bookmarkedIcon.isDisplayed()).to.be.false;
  });

  it('TC-115: User can add notes to study material', async () => {
    const notesBtn = await $('~add_notes_button');
    await notesBtn.click();
    await browser.pause(500);
    const notesInput = await $('~notes_input');
    await notesInput.setValue('Important concept to remember');
    const saveNotesBtn = await $('~save_notes_button');
    await saveNotesBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-116: User can edit notes', async () => {
    const editNotesBtn = await $('~edit_notes_button');
    await editNotesBtn.click();
    await browser.pause(500);
    const notesInput = await $('~notes_input');
    await notesInput.clearValue();
    await notesInput.setValue('Updated important concept');
    const saveNotesBtn = await $('~save_notes_button');
    await saveNotesBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-117: User can delete notes', async () => {
    const deleteNotesBtn = await $('~delete_notes_button');
    await deleteNotesBtn.click();
    await browser.pause(500);
    const confirmBtn = await $('~confirm_delete_button');
    await confirmBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.getText()).to.include('Notes deleted');
  });

  it('TC-118: Study material shows reading time', async () => {
    const readingTime = await $('~reading_time');
    expect(await readingTime.isDisplayed()).to.be.true;
  });

  it('TC-119: User can filter materials by subject', async () => {
    const filterBtn = await $('~filter_button');
    await filterBtn.click();
    await browser.pause(500);
    const subjectFilter = await $('~filter_subject');
    await subjectFilter.click();
    await browser.pause(1000);
    const filteredMaterials = await $('~filtered_materials');
    expect(await filteredMaterials.isDisplayed()).to.be.true;
  });

  it('TC-120: User can sort materials by difficulty', async () => {
    const sortBtn = await $('~sort_button');
    await sortBtn.click();
    await browser.pause(500);
    const sortByDifficulty = await $('~sort_difficulty');
    await sortByDifficulty.click();
    await browser.pause(1000);
    const sortedMaterials = await $('~sorted_materials');
    expect(await sortedMaterials.isDisplayed()).to.be.true;
  });

  it('TC-121: User can search for study material', async () => {
    const searchInput = await $('~search_materials_input');
    await searchInput.setValue('Algebra');
    await browser.pause(1000);
    const searchResults = await $('~search_results');
    expect(await searchResults.isDisplayed()).to.be.true;
  });

  it('TC-122: Study material displays related topics', async () => {
    const relatedTopics = await $('~related_topics_section');
    expect(await relatedTopics.isDisplayed()).to.be.true;
  });

  it('TC-123: User can navigate to related topic', async () => {
    const relatedTopic = await $('~related_topic_1');
    await relatedTopic.click();
    await browser.pause(2000);
    const materialContent = await $('~material_content');
    expect(await materialContent.isDisplayed()).to.be.true;
  });

  it('TC-124: Study material shows video content', async () => {
    const video = await $('~material_video');
    expect(await video.isDisplayed()).to.be.true;
  });

  it('TC-125: User can play video content', async () => {
    const playBtn = await $('~play_video_button');
    await playBtn.click();
    await browser.pause(1000);
    const videoPlayer = await $('~video_player');
    expect(await videoPlayer.isDisplayed()).to.be.true;
  });

  it('TC-126: User can pause video', async () => {
    const pauseBtn = await $('~pause_button');
    await pauseBtn.click();
    await browser.pause(500);
    const isPaused = await browser.executeScript('return document.querySelector("video").paused');
    expect(isPaused).to.be.true;
  });

  it('TC-127: User can skip forward in video', async () => {
    const skipBtn = await $('~skip_forward_button');
    await skipBtn.click();
    await browser.pause(500);
    // Verify current time increased
  });

  it('TC-128: User can skip backward in video', async () => {
    const skipBtn = await $('~skip_backward_button');
    await skipBtn.click();
    await browser.pause(500);
    // Verify current time decreased
  });

  it('TC-129: User can adjust video volume', async () => {
    const volumeSlider = await $('~volume_slider');
    await volumeSlider.setValue(50);
    await browser.pause(500);
    // Verify volume changed
  });

  it('TC-130: Study material shows images', async () => {
    const image = await $('~material_image');
    expect(await image.isDisplayed()).to.be.true;
  });

  it('TC-131: User can view full screen image', async () => {
    const image = await $('~material_image');
    await image.click();
    await browser.pause(1000);
    const fullscreenImage = await $('~fullscreen_image');
    expect(await fullscreenImage.isDisplayed()).to.be.true;
  });

  it('TC-132: User can close fullscreen image', async () => {
    const closeBtn = await $('~close_fullscreen_button');
    await closeBtn.click();
    await browser.pause(500);
    const fullscreenImage = await $('~fullscreen_image');
    expect(await fullscreenImage.isDisplayed()).to.be.false;
  });

  it('TC-133: Study material shows diagrams', async () => {
    const diagram = await $('~material_diagram');
    expect(await diagram.isDisplayed()).to.be.true;
  });

  it('TC-134: User can zoom diagram', async () => {
    const diagram = await $('~material_diagram');
    await diagram.click();
    await browser.pause(500);
    const zoomIn = await $('~zoom_in_button');
    await zoomIn.click();
    await browser.pause(500);
    // Verify diagram zoomed
  });

  it('TC-135: Study material shows quiz button', async () => {
    const quizBtn = await $('~related_quiz_button');
    expect(await quizBtn.isDisplayed()).to.be.true;
  });

  it('TC-136: User can start related quiz', async () => {
    const quizBtn = await $('~related_quiz_button');
    await quizBtn.click();
    await browser.pause(2000);
    const quizScreen = await $('~quiz_screen');
    expect(await quizScreen.isDisplayed()).to.be.true;
  });

  it('TC-137: Study material shows print option', async () => {
    const printBtn = await $('~print_material_button');
    expect(await printBtn.isDisplayed()).to.be.true;
  });

  it('TC-138: User can print study material', async () => {
    const printBtn = await $('~print_material_button');
    await printBtn.click();
    await browser.pause(2000);
    // Verify print dialog
  });

  it('TC-139: Study material shows download option', async () => {
    const downloadBtn = await $('~download_material_button');
    expect(await downloadBtn.isDisplayed()).to.be.true;
  });

  it('TC-140: User can download study material', async () => {
    const downloadBtn = await $('~download_material_button');
    await downloadBtn.click();
    await browser.pause(2000);
    const notification = await $('~download_notification');
    expect(await notification.isDisplayed()).to.be.true;
  });

  it('TC-141: Study material shows share option', async () => {
    const shareBtn = await $('~share_material_button');
    expect(await shareBtn.isDisplayed()).to.be.true;
  });

  it('TC-142: User can share study material', async () => {
    const shareBtn = await $('~share_material_button');
    await shareBtn.click();
    await browser.pause(1000);
    const shareMenu = await $('~share_menu');
    expect(await shareMenu.isDisplayed()).to.be.true;
  });

  it('TC-143: Study material marks as completed', async () => {
    const completeBtn = await $('~mark_complete_button');
    await completeBtn.click();
    await browser.pause(1000);
    const completedIcon = await $('~completed_icon');
    expect(await completedIcon.isDisplayed()).to.be.true;
  });

  it('TC-144: Study material shows time spent', async () => {
    const timeSpent = await $('~time_spent');
    expect(await timeSpent.isDisplayed()).to.be.true;
  });

  it('TC-145: Study material shows completion status', async () => {
    const completionStatus = await $('~completion_status');
    expect(await completionStatus.isDisplayed()).to.be.true;
  });

  it('TC-146: User can rate study material', async () => {
    const ratingStars = await $('~rating_stars');
    await ratingStars.click();
    await browser.pause(500);
    const rating = await $('~material_rating');
    expect(await rating.isDisplayed()).to.be.true;
  });

  it('TC-147: User can leave review for material', async () => {
    const reviewInput = await $('~review_input');
    await reviewInput.setValue('Great material!');
    const submitReview = await $('~submit_review_button');
    await submitReview.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-148: Study material shows user reviews', async () => {
    const reviewsList = await $('~reviews_list');
    expect(await reviewsList.isDisplayed()).to.be.true;
  });

  it('TC-149: Study material is responsive on mobile', async () => {
    await browser.setWindowSize(375, 667);
    const materialContent = await $('~material_content');
    expect(await materialContent.isDisplayed()).to.be.true;
    await browser.setWindowSize(1280, 1024);
  });

  it('TC-150: Study material loads within acceptable time', async () => {
    const startTime = Date.now();
    const material = await $('~material_item_1');
    await material.click();
    const materialContent = await $('~material_content');
    await materialContent.waitForDisplayed();
    const loadTime = Date.now() - startTime;
    expect(loadTime).to.be.below(5000);
  });
});
