package cn.nongph.jiamei.pay.wechat;

import java.util.Random;

public class RandomStringGenerator{
  public static String getRandomStringByLength(int length){
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++){
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }
}

