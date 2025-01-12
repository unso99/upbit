package com.example.upbit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChartController {

  @GetMapping("/chart")
  public String chartInfo(){
    return "chart";
  }

}
