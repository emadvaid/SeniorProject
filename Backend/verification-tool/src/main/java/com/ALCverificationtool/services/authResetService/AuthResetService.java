package com.ALCverificationtool.services.authResetService;

import com.ALCverificationtool.models.ResetToken;
import com.ALCverificationtool.models.UserRec;

public interface AuthResetService {

    void resetPasswordWithResetToken(String resetTokenId, String newPassword);

    ResetToken createPasswordResetToken(UserRec userRec);

    ResetToken createPasswordResetToken(UserRec userRec, boolean newUser);

}
