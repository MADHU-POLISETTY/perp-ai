import * as fs from 'fs';
import * as path from 'path';

export default class Logger {
    private static logFile = path.join(process.cwd(), 'logs', 'test_execution.log');

    private static ensureDirExists() {
        const dir = path.dirname(this.logFile);
        if (!fs.existsSync(dir)) {
            fs.mkdirSync(dir, { recursive: true });
        }
    }

    static info(message: string) {
        this.log('INFO', message);
    }

    static error(message: string) {
        this.log('ERROR', message);
    }

    static debug(message: string) {
        this.log('DEBUG', message);
    }

    private static log(level: string, message: string) {
        this.ensureDirExists();
        const timestamp = new Date().toISOString();
        const formatted = `[${timestamp}] [${level}] ${message}`;
        console.log(formatted);
        fs.appendFileSync(this.logFile, formatted + '\n', 'utf8');
    }
}
