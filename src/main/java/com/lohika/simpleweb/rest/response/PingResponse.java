package com.lohika.simpleweb.rest.response;

public class PingResponse {

  private String name;

  public PingResponse() {
    name = "PONG";
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    //this.name = name;
  }

}
