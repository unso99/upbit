package com.example.upbit.controller;

import com.example.upbit.Dto.MyAccountDto;
import com.example.upbit.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
  private final AccountService accountService;

  @GetMapping("/")
  public String home(Model model) {

    MyAccountDto myAccountDto = accountService.getMyAccount();

    model.addAttribute("availableBalance",myAccountDto.getAvailableBalance());

    return "home";
  }
}
