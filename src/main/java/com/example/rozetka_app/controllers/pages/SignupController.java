package com.example.rozetka_app.controllers.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller("/signup")
@PreAuthorize("isAnonymous()")
public class SignupController {
}
