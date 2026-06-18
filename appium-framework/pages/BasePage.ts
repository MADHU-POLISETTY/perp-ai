import { browser } from '@wdio/globals';

export default class BasePage {
    /**
     * Find element using any selector
     */
    async getElement(selector: string) {
        const el = await $(selector);
        await el.waitForExist({ timeout: 10000 });
        return el;
    }

    /**
     * Click on an element
     */
    async click(selector: string) {
        const el = await this.getElement(selector);
        await el.waitForClickable({ timeout: 5000 });
        await el.click();
    }

    /**
     * Clear and enter text in an input field
     */
    async enterText(selector: string, text: string) {
        const el = await this.getElement(selector);
        await el.clearValue();
        await el.setValue(text);
    }

    /**
     * Check if element is displayed
     */
    async isDisplayed(selector: string): Promise<boolean> {
        try {
            const el = await $(selector);
            return await el.isDisplayed();
        } catch {
            return false;
        }
    }

    /**
     * Get text of an element
     */
    async getText(selector: string): Promise<string> {
        const el = await this.getElement(selector);
        return await el.getText();
    }

    /**
     * Hide keyboard
     */
    async hideKeyboard() {
        try {
            if (await browser.isKeyboardShown()) {
                await browser.hideKeyboard();
            }
        } catch (err) {
            console.log('Keyboard already hidden or not supported.');
        }
    }

    /**
     * Perform swipe gesture
     */
    async swipe(startX: number, startY: number, endX: number, endY: number, duration = 800) {
        await browser.action('pointer')
            .move({ duration: 0, x: startX, y: startY })
            .down({ button: 0 })
            .move({ duration, x: endX, y: endY })
            .up({ button: 0 })
            .perform();
    }

    /**
     * Swipe Down gesture
     */
    async swipeDown() {
        const size = await browser.getWindowSize();
        const startX = Math.round(size.width * 0.5);
        const startY = Math.round(size.height * 0.8);
        const endY = Math.round(size.height * 0.2);
        await this.swipe(startX, startY, startX, endY);
    }

    /**
     * Swipe Up gesture
     */
    async swipeUp() {
        const size = await browser.getWindowSize();
        const startX = Math.round(size.width * 0.5);
        const startY = Math.round(size.height * 0.2);
        const endY = Math.round(size.height * 0.8);
        await this.swipe(startX, startY, startX, endY);
    }
}
