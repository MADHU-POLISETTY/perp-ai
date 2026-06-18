import { browser } from '@wdio/globals';
import * as fs from 'fs';
import * as path from 'path';
import Logger from './Logger';

export default class Video {
    static async startRecording() {
        try {
            Logger.info('Starting screen recording...');
            await browser.startRecordingScreen({
                timeLimit: '180',
                videoSize: '1280x720',
                bitRate: '4000000'
            });
        } catch (err: any) {
            Logger.error(`Failed to start screen recording: ${err.message}`);
        }
    }

    static async stopRecording(testName: string): Promise<string> {
        try {
            Logger.info('Stopping screen recording...');
            const base64Data = await browser.stopRecordingScreen();
            
            const dir = path.join(process.cwd(), 'videos');
            if (!fs.existsSync(dir)) {
                fs.mkdirSync(dir, { recursive: true });
            }
            const cleanName = testName.replace(/[^a-zA-Z0-9]/g, '_');
            const filepath = path.join(dir, `${cleanName}_${Date.now()}.mp4`);
            
            fs.writeFileSync(filepath, Buffer.from(base64Data, 'base64'));
            Logger.info(`Screen recording saved successfully: ${filepath}`);
            return filepath;
        } catch (err: any) {
            Logger.error(`Failed to stop screen recording: ${err.message}`);
            return '';
        }
    }
}
