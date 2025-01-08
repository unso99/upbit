package com.example.upbit.model.dto;


import com.example.upbit.model.Enum.TrendType;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TickerDto {

  //  종목 구분 코드
  private String market;
  /*
  최근 거래 일자(UTC)
  포맷 : yyyyMMdd
   */
  private String trade_date;
  /*
  최근 거래 시각(UTC)
  포맷 : HHmmss
   */
  private String trade_time;
  /*
  최근 거래 일자(KST)
  포맷 : yyyyMMdd
   */
  private String trade_date_kst;
  /*
  최근 거래 시각(KST)
  포맷 : HHmmss
   */
  private String trade_time_kst;
  /*
  최근 거래 일시(UTC)
  포맷 : Unix TimeStamp
   */
  private Long trade_timestamp;
  //시가
  private Double opening_price;
  //고가
  private Double high_price;
  //저가
  private Double low_price;
  //종가(현재가)
  private Double trade_price;
  //KRW 포맷
  private String trade_price_format;
  //전일 종가(UTC 0시 기준)
  private Double prev_closing_price;
  /*
  EVEN : 보합
  RISE : 상승
  FALL : 하락
   */
  private TrendType change;
  // 변화액의 절대값
  private Double change_price;
  //변화율의 절대값
  private Double change_rate;
  //부호가 있는 변화액
  private Double signed_change_price;
  //부호가 있는 변화율
  private Double signed_change_rate;
  // 변화율 포맷
  private String signed_change_rate_format;
  //가장 최근 거래량
  private Double trade_volume;
  //누적 거래대금(UTC 0시 기준)
  private Double acc_trade_price;
  //24시간 누적 거래대금
  private Double acc_trade_price_24h;
  //누적 거래량(UTC 0시 기준)
  private Double acc_trade_volume;
  //누적 거래량 포맷
  private String acc_trade_volume_format;
  //24시간 누적 거래량
  private Double acc_trade_volume_24h;
  //52주 신고가
  private Double highest_52_week_price;
  /*
  52주 신고가 달성일
  포맷: yyyy-MM-dd
   */
  private String highest_52_week_date;
  //52주 신저가
  private Double lowest_52_week_price;
  /*
  52주 신저가 달성일
  포맷: yyyy-MM-dd
   */
  private String lowest_52_week_date;
  //타임스탬프
  private Long timestamp;

  //KRW 포맷
  public void setTrade_price_format(){
    this.trade_price_format = getFormattedTradePrice();
  }

  public void setSigned_change_rate_format(){
    this.signed_change_rate_format = getFormattedChangeRate();
  }

  public void setAcc_trade_volume_format() {
    this.acc_trade_volume_format = getFormattedVolume();
  }

  public String getFormattedTradePrice() {
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    numberFormat.setMaximumFractionDigits(0);  // 소수점 이하 버림
    return numberFormat.format(this.trade_price) + " KRW";
  }

  public String getFormattedChangeRate() {
    DecimalFormat decimalFormat = new DecimalFormat("0.00%");

    return decimalFormat.format(this.signed_change_rate);
  }

  public String getFormattedVolume() {
    String unit = "";
    double formattedValue = this.acc_trade_volume;

    // 1,000,000,000 이상일 경우 B(십억)
    if (this.acc_trade_volume >= 1_000_000_000) {
      formattedValue = this.acc_trade_volume / 1_000_000_000;
      unit = "B";
    }
    // 1,000,000 이상일 경우 M(백만)
    else if (this.acc_trade_volume >= 1_000_000) {
      formattedValue = this.acc_trade_volume / 1_000_000;
      unit = "M";
    }
    // 1,000 이상일 경우 K(천)
    else if (this.acc_trade_volume >= 1_000) {
      formattedValue = this.acc_trade_volume / 1_000;
      unit = "K";
    }

    // 소수점 3자리까지 포맷팅
    return String.format("%.3f%s", formattedValue, unit);
  }
}
