package com.lohika.simpleweb.domain;

import com.lohika.simpleweb.dao.ConnectionManager;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = ConnectionManager.COLLECTION_TICKET)
public class Ticket extends BasicNamedDbEntity {

  private long created;

  public long getCreated() {
    return created;
  }

  public void setCreated(long created) {
    this.created = created;
  }
}
