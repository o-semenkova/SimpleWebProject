package com.lohika.simpleweb.dao;

import com.lohika.simpleweb.domain.Ticket;
import org.springframework.stereotype.Repository;


@Repository
public class TicketDao extends AbstractDao<Ticket> {

  @Override
  public Class<Ticket> getDomain() {
    return Ticket.class;
  }
}
