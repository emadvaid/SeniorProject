package com.ALCverificationtool.services.authResetService;

import com.ALCverificationtool.models.ResetEntity;
import com.ALCverificationtool.models.User;

public interface AuthResetService {

    ResetEntity resetUserPassword(User user);
}
