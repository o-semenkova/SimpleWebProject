package com.lohika.simpleweb.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public abstract class BasicEntity implements Serializable {

  private static final long serialVersionUID = 6162099238582871373L;

}
