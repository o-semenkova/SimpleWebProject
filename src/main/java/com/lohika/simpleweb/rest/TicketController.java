package com.lohika.simpleweb.rest;

import com.lohika.simpleweb.domain.Ticket;
import com.lohika.simpleweb.service.AbstractServiceDb;
import com.lohika.simpleweb.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = RestConst.URL_TICKET, produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketController extends AbstractController<Ticket> {

  @Autowired
  private TicketService service;

  @Override
  protected AbstractServiceDb<Ticket> getService() {
    return service;
  }
}
