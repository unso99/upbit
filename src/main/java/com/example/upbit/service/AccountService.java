package com.example.upbit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.upbit.Dto.MyAccountDto;
import com.example.upbit.Dto.MyBankDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
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
public class AccountService {

  @Value("${Access-Key}")
  private String accessKey;

  @Value("${Secret-Key}")
  private String secretKey;

  private String serverUrl = "https://api.upbit.com";

  public MyAccountDto getMyAccount() {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String jwtToken = JWT.create()
        .withClaim("access_key", accessKey)
        .withClaim("nonce", UUID.randomUUID().toString())
        .sign(algorithm);

    String authenticationToken = "Bearer " + jwtToken;

    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpGet request = new HttpGet(serverUrl + "/v1/accounts");
      request.setHeader("Content-Type", "application/json");
      request.addHeader("Authorization", authenticationToken);

      HttpResponse response = client.execute(request);
      HttpEntity entity = response.getEntity();

      String entityString = EntityUtils.toString(entity, "UTF-8");

      List<MyBankDto> myBankDtoList = new Gson().fromJson(entityString,
          new TypeToken<List<MyBankDto>>() {
          }.getType());

      int availableBalance = 0;

      for(MyBankDto myBank : myBankDtoList) {
        if (myBank.getCurrency().equals("KRW")) {
          availableBalance = (int) Double.parseDouble(myBank.getBalance());
          break;
        }
      }

      DecimalFormat decimalFormat = new DecimalFormat();
      String result = decimalFormat.format(availableBalance);

      return MyAccountDto.builder().availableBalance("￦" + result).build();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
