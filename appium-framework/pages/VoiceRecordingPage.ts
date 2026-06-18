import BasePage from './BasePage';

export default class VoiceRecordingPage extends BasePage {
    get voiceRecordButton() { return '//*[@resource-id="btn-toggle-voice" or contains(@content-desc, "voice") or contains(@text, "Voice")]'; }
    get recordingStatus() { return '//*[contains(@text, "Recording") or contains(@text, "Listening")]'; }

    async startRecording() {
        if (await this.isDisplayed(this.voiceRecordButton)) {
            await this.click(this.voiceRecordButton);
        }
    }

    async stopRecording() {
        if (await this.isDisplayed(this.voiceRecordButton)) {
            await this.click(this.voiceRecordButton);
        }
    }
}
