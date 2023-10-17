package com.realfinal.toot.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kospi32")
@Data
public class Kospi32Config {
  public List<String> company1;
  public List<String> company2;
  public List<String> company3;
  public List<String> company4;
  public List<String> totalCompany;

}