import type { Options } from '@wdio/types';
import * as fs from 'fs';
import * as path from 'path';

export const config: Options.Testrunner = {
    runner: 'local',
    autoCompileOpts: {
        autoCompile: true,
        tsNodeOpts: {
            project: './tsconfig.json',
            transpileOnly: true
        }
    },
    
    specs: [
        './tests/**/*.test.ts'
    ],
    exclude: [],
    
    maxInstances: 1,
    
    capabilities: [{
        platformName: 'Android',
        'appium:deviceName': 'Android Emulator',
        'appium:automationName': 'UiAutomator2',
        'appium:app': path.join(process.cwd(), '../app/build/outputs/apk/debug/app-debug.apk'),
        'appium:appPackage': 'com.example.perp_ai',
        'appium:appActivity': 'com.example.perp_ai.MainActivity',
        'appium:noReset': true,
        'appium:newCommandTimeout': 240,
        'appium:gpsEnabled': true
    }],
    
    logLevel: 'info',
    bail: 0,
    baseUrl: 'http://localhost',
    waitforTimeout: 10000,
    connectionRetryTimeout: 120000,
    connectionRetryCount: 3,
    
    services: [], // Executed with pre-started Appium server in CI/CD for better control
    
    framework: 'mocha',
    reporters: ['spec'],
    
    mochaOpts: {
        ui: 'bdd',
        timeout: 600000
    },

    afterTest: async function(test, context, { error, result, duration, passed, retries }) {
        if (!passed) {
            const screenshotsDir = path.join(__dirname, 'screenshots');
            if (!fs.existsSync(screenshotsDir)) {
                fs.mkdirSync(screenshotsDir, { recursive: true });
            }
            const filename = `${test.title.replace(/[^a-zA-Z0-9]/g, '_')}_failed.png`;
            const filepath = path.join(screenshotsDir, filename);
            try {
                await browser.saveScreenshot(filepath);
                console.log(`[Evidence] Screenshot saved to ${filepath}`);
            } catch (err) {
                console.error(`Failed to save screenshot on test failure: ${err}`);
            }
        }
    }
};
