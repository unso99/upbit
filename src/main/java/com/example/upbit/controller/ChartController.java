package com.example.upbit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ChartController {

  @GetMapping("/chart")
  public String chartInfo(
      @RequestParam(name = "ticker", required = false, defaultValue = "KRW-BTC") String ticker,
      Model model) {

    model.addAttribute("ticker", ticker);
    return "chart";
  }

}
