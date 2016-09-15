package com.lohika.simpleweb.service;

import com.lohika.simpleweb.dao.AbstractDao;
import com.lohika.simpleweb.dao.TicketDao;
import com.lohika.simpleweb.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService extends AbstractServiceDb<Ticket> {

  @Autowired
  private TicketDao dao;


  @Override
  public Class<Ticket> getDomain() {
    return Ticket.class;
  }

  @Override
  public AbstractDao<Ticket> getDao() {
    return dao;
  }

}
