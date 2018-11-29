package com.ALCverificationtool.controllers.passwords;

import com.ALCverificationtool.services.ServiceException;
import com.ALCverificationtool.services.authResetService.AuthResetService;
import com.ALCverificationtool.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordController {

    private final AuthResetService authResetService;
    private final UserService userService;

    @Autowired
    public PasswordController(AuthResetService authResetService, UserService userService) {
        this.authResetService = authResetService;
        this.userService = userService;
    }

    @GetMapping(value = "/private/encode/{rawPassword}")
    public ResponseEntity<String> resetPassword(@PathVariable String rawPassword) {

        String encodedPass = this.authResetService.encodePassword(rawPassword);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(encodedPass, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(value = "/password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {

        this.authResetService.resetPasswordWithResetToken(request.getResetId(), request.getNewPassword());

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>("{'status': 'Success'}", headers, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public void handleUserAuthorizationException() {
    }
}