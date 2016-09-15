package com.lohika.simpleweb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BasicNamedDbEntity extends BasicDbEntity {

  public static final String FIELD_NAME = "name";

  private static final long serialVersionUID = -4720020904449211330L;

  @Indexed
  @JsonProperty(required = true)
  //@JsonSerialize
  protected String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
