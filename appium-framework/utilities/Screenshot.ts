import { browser } from '@wdio/globals';
import * as fs from 'fs';
import * as path from 'path';
import Logger from './Logger';

export default class Screenshot {
    static async capture(name: string): Promise<string> {
        const dir = path.join(process.cwd(), 'screenshots');
        if (!fs.existsSync(dir)) {
            fs.mkdirSync(dir, { recursive: true });
        }
        const cleanName = name.replace(/[^a-zA-Z0-9]/g, '_');
        const filename = `${cleanName}_${Date.now()}.png`;
        const filepath = path.join(dir, filename);
        
        try {
            await browser.saveScreenshot(filepath);
            Logger.info(`Screenshot captured successfully: ${filepath}`);
            return filepath;
        } catch (err: any) {
            Logger.error(`Failed to capture screenshot: ${err.message}`);
            return '';
        }
    }
}
