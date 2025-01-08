package com.example.upbit.service;

import com.example.upbit.model.dto.TickerDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:config.properties")
public class TickerService {

  private String serverUrl = "https://api.upbit.com";

  public TickerDto getTicker(String ticker) {

    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(serverUrl + "/v1/ticker?markets=" + ticker);
      request.setHeader("Content-Type", "application/json");

      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();

      String entityString = EntityUtils.toString(entity, "UTF-8");

      List<TickerDto> tickerDtoList = new Gson().fromJson(entityString,
          new TypeToken<List<TickerDto>>() {
          }.getType());
      TickerDto result = tickerDtoList.get(0);
      result.setTrade_price_format();
      result.setSigned_change_rate_format();
      //로그용
      System.out.println(result);
      return result;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<TickerDto> getAllTicker() {

    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(serverUrl + "/v1/ticker/all?quote_currencies=KRW");
      request.setHeader("Content-Type", "application/json");

      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();

      String entityString = EntityUtils.toString(entity, "UTF-8");

      List<TickerDto> tickerDtoList = new Gson().fromJson(entityString,
          new TypeToken<List<TickerDto>>() {
          }.getType());
      for (TickerDto dto : tickerDtoList) {
        dto.setTrade_price_format();
        dto.setSigned_change_rate_format();
        dto.setAcc_trade_volume_format();
      }

      return tickerDtoList;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
