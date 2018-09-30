package com.ALCverificationtool.controllers.passwords;

public class ResetPasswordRequest {
    String resetId;
    String newPassword;

    // String twoFactorAuthCode;  - possibly add a two factor authentication code

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String requestId, String newPassword) {
        this.resetId = requestId;
        this.newPassword = newPassword;
    }

    public String getResetId() {
        return resetId;
    }

    public void setResetId(String resetId) {
        this.resetId = resetId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

