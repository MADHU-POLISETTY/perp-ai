import { expect } from 'chai';

describe('Authentication Tests (1-20)', () => {
  // Login Tests
  it('TC-001: User can login with valid credentials', async () => {
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
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  it('TC-002: User cannot login with invalid email', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const emailInput = await $('~email_input');
    await emailInput.setValue('invalidemail');
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('password123');
    const submitBtn = await $('~login_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Invalid email');
  });

  it('TC-003: User cannot login with empty email', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('password123');
    const submitBtn = await $('~login_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Email is required');
  });

  it('TC-004: User cannot login with empty password', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const emailInput = await $('~email_input');
    await emailInput.setValue('testuser@example.com');
    const submitBtn = await $('~login_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Password is required');
  });

  it('TC-005: User cannot login with wrong password', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const emailInput = await $('~email_input');
    await emailInput.setValue('testuser@example.com');
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('wrongPassword123');
    const submitBtn = await $('~login_submit');
    await submitBtn.click();
    await browser.pause(2000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Invalid credentials');
  });

  // Signup Tests
  it('TC-006: User can signup with valid details', async () => {
    await browser.url('http://localhost');
    const signupBtn = await $('~signup_button');
    await signupBtn.click();
    const nameInput = await $('~name_input');
    await nameInput.setValue('Test User');
    const emailInput = await $('~email_input');
    await emailInput.setValue(`testuser${Date.now()}@example.com`);
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('ValidPass123');
    const confirmInput = await $('~confirm_password_input');
    await confirmInput.setValue('ValidPass123');
    const submitBtn = await $('~signup_submit');
    await submitBtn.click();
    await browser.pause(2000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-007: User cannot signup with existing email', async () => {
    await browser.url('http://localhost');
    const signupBtn = await $('~signup_button');
    await signupBtn.click();
    const nameInput = await $('~name_input');
    await nameInput.setValue('Test User');
    const emailInput = await $('~email_input');
    await emailInput.setValue('existing@example.com');
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('ValidPass123');
    const confirmInput = await $('~confirm_password_input');
    await confirmInput.setValue('ValidPass123');
    const submitBtn = await $('~signup_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Email already exists');
  });

  it('TC-008: User cannot signup with password mismatch', async () => {
    await browser.url('http://localhost');
    const signupBtn = await $('~signup_button');
    await signupBtn.click();
    const nameInput = await $('~name_input');
    await nameInput.setValue('Test User');
    const emailInput = await $('~email_input');
    await emailInput.setValue(`testuser${Date.now()}@example.com`);
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('ValidPass123');
    const confirmInput = await $('~confirm_password_input');
    await confirmInput.setValue('DifferentPass123');
    const submitBtn = await $('~signup_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Passwords do not match');
  });

  it('TC-009: User cannot signup with weak password', async () => {
    await browser.url('http://localhost');
    const signupBtn = await $('~signup_button');
    await signupBtn.click();
    const nameInput = await $('~name_input');
    await nameInput.setValue('Test User');
    const emailInput = await $('~email_input');
    await emailInput.setValue(`testuser${Date.now()}@example.com`);
    const passwordInput = await $('~password_input');
    await passwordInput.setValue('weak');
    const confirmInput = await $('~confirm_password_input');
    await confirmInput.setValue('weak');
    const submitBtn = await $('~signup_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Password must be at least 8 characters');
  });

  it('TC-010: User can navigate to login from signup', async () => {
    await browser.url('http://localhost');
    const signupBtn = await $('~signup_button');
    await signupBtn.click();
    const loginLink = await $('~login_link');
    await loginLink.click();
    await browser.pause(1000);
    const emailInput = await $('~email_input');
    expect(await emailInput.isDisplayed()).to.be.true;
  });

  // Password Reset Tests
  it('TC-011: User can initiate password reset', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const forgotBtn = await $('~forgot_password_link');
    await forgotBtn.click();
    await browser.pause(1000);
    const emailInput = await $('~reset_email_input');
    await emailInput.setValue('testuser@example.com');
    const submitBtn = await $('~reset_submit');
    await submitBtn.click();
    await browser.pause(2000);
    const successMsg = await $('~success_message');
    expect(await successMsg.isDisplayed()).to.be.true;
  });

  it('TC-012: Password reset with invalid email shows error', async () => {
    await browser.url('http://localhost');
    const loginBtn = await $('~login_button');
    await loginBtn.click();
    const forgotBtn = await $('~forgot_password_link');
    await forgotBtn.click();
    await browser.pause(1000);
    const emailInput = await $('~reset_email_input');
    await emailInput.setValue('nonexistent@example.com');
    const submitBtn = await $('~reset_submit');
    await submitBtn.click();
    await browser.pause(1000);
    const errorMsg = await $('~error_message');
    expect(await errorMsg.getText()).to.include('Email not found');
  });

  it('TC-013: User can logout successfully', async () => {
    // Assumes user is already logged in
    const logoutBtn = await $('~logout_button');
    await logoutBtn.click();
    await browser.pause(1000);
    const loginScreen = await $('~login_button');
    expect(await loginScreen.isDisplayed()).to.be.true;
  });

  it('TC-014: Session expires after inactivity', async () => {
    // This test would require waiting 30 minutes in real scenario
    // For testing: configure shorter timeout or mock time
    await browser.pause(31 * 60 * 1000); // 31 minutes
    const loginScreen = await $('~login_button');
    expect(await loginScreen.isDisplayed()).to.be.true;
  });

  it('TC-015: User cannot access protected pages without login', async () => {
    await browser.executeScript('localStorage.clear()');
    await browser.url('http://localhost/dashboard');
    await browser.pause(1000);
    const loginBtn = await $('~login_button');
    expect(await loginBtn.isDisplayed()).to.be.true;
  });

  it('TC-016: Social login with Google', async () => {
    await browser.url('http://localhost');
    const googleBtn = await $('~google_login_button');
    await googleBtn.click();
    await browser.pause(3000);
    // Additional logic to handle OAuth flow
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  it('TC-017: Social login with Facebook', async () => {
    await browser.url('http://localhost');
    const facebookBtn = await $('~facebook_login_button');
    await facebookBtn.click();
    await browser.pause(3000);
    const dashboard = await $('~dashboard_screen');
    expect(await dashboard.isDisplayed()).to.be.true;
  });

  it('TC-018: Two-factor authentication setup', async () => {
    const settingsBtn = await $('~settings_button');
    await settingsBtn.click();
    const twoFABtn = await $('~enable_2fa_button');
    await twoFABtn.click();
    await browser.pause(1000);
    const qrCode = await $('~qr_code');
    expect(await qrCode.isDisplayed()).to.be.true;
  });

  it('TC-019: Two-factor authentication verification', async () => {
    const otpInput = await $('~otp_input');
    await otpInput.setValue('123456');
    const verifyBtn = await $('~verify_otp_button');
    await verifyBtn.click();
    await browser.pause(1000);
    const successMsg = await $('~success_message');
    expect(await successMsg.getText()).to.include('2FA enabled');
  });

  it('TC-020: User profile auto-loads on successful login', async () => {
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
    const profileName = await $('~profile_name');
    expect(await profileName.getText()).to.equal('Test User');
  });
});
