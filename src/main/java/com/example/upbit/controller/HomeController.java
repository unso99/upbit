package com.example.upbit.controller;

import com.example.upbit.model.dto.MyAccountDto;
import com.example.upbit.model.dto.TickerDto;
import com.example.upbit.service.AccountService;
import com.example.upbit.service.TickerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
  private final AccountService accountService;
  private final TickerService tickerService;

  @GetMapping("/")
  public String home(Model model) {

    MyAccountDto myAccountDto = accountService.getMyAccount();
    TickerDto btc = tickerService.getTicker("KRW-BTC");
    List<TickerDto> allTicker = tickerService.getAllTicker();

    model.addAttribute("availableBalance",myAccountDto.getAvailableBalance());
    model.addAttribute("BTC",btc);
    model.addAttribute("tickers",allTicker);

    return "home";
  }
}
