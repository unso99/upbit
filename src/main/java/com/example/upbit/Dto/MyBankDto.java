package com.example.upbit.Dto;

import lombok.Getter;

@Getter
public class MyBankDto {

  private String currency;
  private String balance;
  private String locked;
  private String avg_buy_price;
  private boolean avg_buy_price_modified;
  private String unit_currency;
  private String korean_name;
}
