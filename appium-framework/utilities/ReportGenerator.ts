import * as fs from 'fs';
import * as path from 'path';
import Logger from './Logger';

interface TestResult {
    id: string;
    category: string;
    component: string;
    scenario: string;
    status: 'PASS' | 'FAIL' | 'SKIP';
    duration: number;
    error: string;
    screenshot: string;
}

export default class ReportGenerator {
    static generateAll(results: TestResult[]) {
        const reportsDir = path.join(process.cwd(), 'reports');
        if (!fs.existsSync(reportsDir)) {
            fs.mkdirSync(reportsDir, { recursive: true });
        }

        this.generateJSON(results, reportsDir);
        this.generateHTML(results, reportsDir);
        this.generateExcel(results, reportsDir);
        this.generatePDF(results, reportsDir);
    }

    private static generateJSON(results: TestResult[], dir: string) {
        const filepath = path.join(dir, 'test_report.json');
        const data = {
            appName: 'Prep-AI',
            timestamp: new Date().toISOString(),
            metrics: this.calculateMetrics(results),
            results: results
        };
        fs.writeFileSync(filepath, JSON.stringify(data, null, 2), 'utf8');
        Logger.info(`JSON report generated successfully: ${filepath}`);
    }

    private static generateHTML(results: TestResult[], dir: string) {
        const filepath = path.join(dir, 'test_report.html');
        const metrics = this.calculateMetrics(results);
        const rows = results.map(r => `
            <tr class="${r.status.toLowerCase()}">
                <td style="text-align: center; font-weight: bold;">${r.id}</td>
                <td style="text-align: center;">${r.category}</td>
                <td>${r.component}</td>
                <td><strong>${r.scenario}</strong></td>
                <td style="text-align: center;"><span class="badge ${r.status.toLowerCase()}">${r.status}</span></td>
                <td style="text-align: center;">${r.duration}s</td>
                <td>${r.error ? `<pre class="error-log">${r.error}</pre>` : '<span class="success-txt">✓ Verified</span>'}</td>
            </tr>
        `).join('');

        const html = `<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Prep-AI E2E Test Report Dashboard</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Outfit', sans-serif; background-color: #08080a; color: #ffffff; margin: 0; padding: 2rem; }
        .container { max-width: 1200px; margin: 0 auto; }
        header { border-bottom: 1px solid #1f1f26; padding-bottom: 1.5rem; margin-bottom: 2rem; display: flex; justify-content: space-between; align-items: center; }
        .logo { font-size: 1.8rem; font-weight: 700; }
        .logo span { background-color: #ffffff; color: #000000; padding: 0.1rem 0.4rem; font-size: 0.8rem; font-weight: 800; margin-left: 0.3rem; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1.5rem; margin-bottom: 2rem; }
        .card { background-color: #111115; border: 1px solid #1f1f26; padding: 1.5rem; position: relative; border-radius: 4px; }
        .card::before { content: ''; position: absolute; top: 0; left: 0; width: 4px; height: 100%; }
        .card.total::before { background-color: #94a3b8; }
        .card.pass::before { background-color: #10b981; }
        .card.fail::before { background-color: #f43f5e; }
        .card.rate::before { background-color: #f59e0b; }
        .card-val { font-size: 2.2rem; font-weight: 700; margin-top: 0.5rem; }
        .card-val.pass { color: #10b981; }
        .card-val.fail { color: #f43f5e; }
        table { width: 100%; border-collapse: collapse; background-color: #111115; border: 1px solid #1f1f26; font-size: 0.9rem; }
        th { background-color: #181822; padding: 1rem; text-align: left; text-transform: uppercase; font-size: 0.75rem; letter-spacing: 0.05em; border-bottom: 1px solid #1f1f26; }
        td { padding: 1rem; border-bottom: 1px solid #1f1f26; vertical-align: middle; }
        tr:hover td { background-color: #181822; }
        .badge { padding: 0.25rem 0.6rem; font-size: 0.75rem; font-weight: 700; border-radius: 2px; text-transform: uppercase; }
        .badge.pass { background-color: rgba(16,185,129,0.1); border: 1px solid rgba(16,185,129,0.2); color: #10b981; }
        .badge.fail { background-color: rgba(244,63,94,0.1); border: 1px solid rgba(244,63,94,0.2); color: #f43f5e; }
        .error-log { background-color: #1f0b0d; color: #fecdd3; border: 1px solid rgba(244,63,94,0.1); padding: 0.5rem; font-family: monospace; font-size: 0.75rem; white-space: pre-wrap; margin: 0; }
        .success-txt { color: #10b981; font-style: italic; }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <div class="logo">Prep-AI<span>E2E</span></div>
            <div>Timestamp: ${new Date().toLocaleString()}</div>
        </header>
        <div class="stats-grid">
            <div class="card total">
                <div>TOTAL TESTS</div>
                <div class="card-val">${metrics.total}</div>
            </div>
            <div class="card pass">
                <div>PASSED</div>
                <div class="card-val pass">${metrics.passed}</div>
            </div>
            <div class="card fail">
                <div>FAILED</div>
                <div class="card-val fail">${metrics.failed}</div>
            </div>
            <div class="card rate">
                <div>PASS RATE</div>
                <div class="card-val" style="color: #f59e0b;">${metrics.passRate}%</div>
            </div>
        </div>
        <table>
            <thead>
                <tr>
                    <th style="width: 80px; text-align: center;">ID</th>
                    <th style="width: 150px; text-align: center;">Category</th>
                    <th style="width: 200px;">Module</th>
                    <th>Scenario</th>
                    <th style="width: 100px; text-align: center;">Status</th>
                    <th style="width: 100px; text-align: center;">Duration</th>
                    <th>Execution Log</th>
                </tr>
            </thead>
            <tbody>
                ${rows}
            </tbody>
        </table>
    </div>
</body>
</html>`;
        fs.writeFileSync(filepath, html, 'utf8');
        Logger.info(`HTML report generated successfully: ${filepath}`);
    }

    private static generateExcel(results: TestResult[], dir: string) {
        const filepath = path.join(dir, 'test_report.xlsx');
        const ExcelJS = require('exceljs');
        const workbook = new ExcelJS.Workbook();
        const sheet = workbook.addWorksheet('Execution Report');

        sheet.views = [{ showGridLines: true }];

        // Header styles
        sheet.mergeCells('A1:G2');
        const titleCell = sheet.getCell('A1');
        titleCell.value = 'Prep-AI - Combined Appium & Selenium Automation Test Report';
        titleCell.font = { name: 'Segoe UI', size: 16, bold: true, color: { argb: 'FFFFFFFF' } };
        titleCell.fill = { type: 'pattern', pattern: 'solid', fgColor: { argb: 'FF1F2937' } };
        titleCell.alignment = { horizontal: 'center', vertical: 'center' };

        // Metrics
        const m = this.calculateMetrics(results);
        sheet.getCell('A4').value = 'Execution Date:';
        sheet.getCell('B4').value = new Date().toLocaleString();
        sheet.getCell('A5').value = 'Total Test Cases:';
        sheet.getCell('B5').value = m.total;
        sheet.getCell('D4').value = 'Passed Tests:';
        sheet.getCell('E4').value = m.passed;
        sheet.getCell('D5').value = 'Failed Tests:';
        sheet.getCell('E5').value = m.failed;
        sheet.getCell('F4').value = 'Pass Rate:';
        sheet.getCell('G4').value = `${m.passRate}%`;

        // Column Headers
        const headers = ['Test ID', 'Category', 'Component / Module', 'Test Scenario', 'Status', 'Execution (s)', 'Error Details / Notes'];
        sheet.getRow(7).values = headers;
        sheet.getRow(7).font = { name: 'Segoe UI', size: 11, bold: true, color: { argb: 'FFFFFFFF' } };
        sheet.getRow(7).fill = { type: 'pattern', pattern: 'solid', fgColor: { argb: 'FF4B5563' } };

        results.forEach((r, idx) => {
            const rowIdx = 8 + idx;
            sheet.getRow(rowIdx).values = [
                r.id,
                r.category,
                r.component,
                r.scenario,
                r.status,
                r.duration,
                r.error || 'Successfully validated.'
            ];
            
            // Status styling
            const statusCell = sheet.getCell(`E${rowIdx}`);
            if (r.status === 'PASS') {
                statusCell.fill = { type: 'pattern', pattern: 'solid', fgColor: { argb: 'FFD1FAE5' } };
                statusCell.font = { color: { argb: 'FF065F46' }, bold: true };
            } else {
                statusCell.fill = { type: 'pattern', pattern: 'solid', fgColor: { argb: 'FFFEE2E2' } };
                statusCell.font = { color: { argb: 'FF991B1B' }, bold: true };
            }
        });

        workbook.xlsx.writeFile(filepath)
            .then(() => Logger.info(`Excel report generated successfully: ${filepath}`))
            .catch((err: any) => Logger.error(`Failed to generate Excel report: ${err.message}`));
    }

    private static generatePDF(results: TestResult[], dir: string) {
        const filepath = path.join(dir, 'test_report.pdf');
        try {
            const PDFDocument = require('pdfkit');
            const doc = new PDFDocument({ margin: 50 });
            doc.pipe(fs.createWriteStream(filepath));

            const m = this.calculateMetrics(results);

            // Title Banner
            doc.fillColor('#1f2937')
               .rect(0, 0, 612, 100)
               .fill();
            doc.fillColor('#ffffff')
               .fontSize(18)
               .text('Prep-AI E2E Automation Testing Summary Report', 50, 40);

            // Date & Stats
            doc.fillColor('#000000').fontSize(11).text(`Generated: ${new Date().toLocaleString()}`, 50, 120);

            doc.fontSize(14).text('Executive Summary', 50, 150);
            doc.fontSize(10).text(`Total executed test scripts: ${m.total}`);
            doc.text(`Successfully validated cases: ${m.passed}`);
            doc.text(`Failed test assertions: ${m.failed}`);
            doc.text(`Quality metric score / Pass Rate: ${m.passRate}%`);

            // Defect details
            doc.fontSize(14).text('Defect Analysis Log', 50, 240);
            let y = 265;
            const failures = results.filter(r => r.status === 'FAIL');
            if (failures.length === 0) {
                doc.fontSize(10).text('No defects detected during E2E assessment cycle. Release recommendation: APPROVED.', 50, y);
            } else {
                failures.forEach(f => {
                    doc.fontSize(10).text(`• [${f.id}] [${f.component}] - ${f.scenario}`, 50, y);
                    doc.text(`  Error: ${f.error.split('\n')[0]}`, 50, y + 15);
                    y += 35;
                });
            }

            doc.end();
            Logger.info(`PDF report generated successfully: ${filepath}`);
        } catch (err: any) {
            Logger.error(`Failed to generate PDF report: ${err.message}`);
        }
    }

    private static calculateMetrics(results: TestResult[]) {
        const total = results.length;
        const passed = results.filter(r => r.status === 'PASS').length;
        const failed = total - passed;
        const passRate = total > 0 ? parseFloat(((passed / total) * 100).toFixed(1)) : 0;
        return { total, passed, failed, passRate };
    }
}
