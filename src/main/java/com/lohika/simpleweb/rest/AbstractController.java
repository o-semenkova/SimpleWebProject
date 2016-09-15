package com.lohika.simpleweb.rest;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.lohika.simpleweb.domain.BasicDbEntity;
import com.lohika.simpleweb.exceptions.BasicException;
import com.lohika.simpleweb.rest.response.CountResponse;
import com.lohika.simpleweb.service.AbstractServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractController<T extends BasicDbEntity> {

  //protected final Logger log = LoggerFactory.getLogger(getClass());

  protected abstract AbstractServiceDb<T> getService();

//  @Autowired
//  protected AuthService authService;

  @Autowired
  private HttpServletRequest request;

  protected String getTenantId() {
    return null;
    //return authService.getTenantId(request);
  }

  protected String getTenantId(String token) {
    return null;
    //return authService.getTenantId(token);
  }


  // find - one
  protected T findOneInternal(final String id, String tenantId) {
    if (Strings.isNullOrEmpty(id)) {
      throw new BasicException("Id cannot be empty");
    }

    return getService().get(id, tenantId);
  }


  // find - all
  protected List<T> findAllInternal(String tenantId) {
    return getService().list(tenantId);
  }


  // save/create/persist
  protected String createInternal(T resource, String tenantId) {
    Preconditions.checkNotNull(resource);
    getService().validateOnCreate(resource);
    return getService().saveNew(resource, tenantId);
  }


  // update
  protected void updateInternal(T resource, String tenantId) {
    Preconditions.checkNotNull(resource);
    getService().saveExisting(resource, tenantId);
  }

  // delete/remove
  protected void deleteByIdInternal(String id, String tenantId) {
    if (Strings.isNullOrEmpty(id)) {
      throw new BasicException("Id cannot be empty");
    }
    getService().delete(id, tenantId);
  }


  // count
  protected long countInternal(String tenantId) {
    return getService().count(tenantId);
  }



  // generic REST operations

  @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_COUNT)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public CountResponse count() {
    String tenantId = getTenantId();
    CountResponse res = new CountResponse();
    res.setCount(countInternal(tenantId));
    return res;
  }


//  @RequestMapping(method = RequestMethod.POST, value = RestConst.URL_CREATE)
//  @ResponseStatus(value = HttpStatus.OK)
//  @ResponseBody
//  public IdResponse create(/*@RequestBody*/ T resource) {
//    return new IdResponse(createInternal(resource, null));
//  }

  @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_FIND)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public T findOne(@PathVariable String itemId) {
    return findOneInternal(itemId, getTenantId());
  }


  @RequestMapping(method = RequestMethod.DELETE, value = RestConst.URL_DELETE)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public void delete(@PathVariable String itemId) {
    deleteByIdInternal(itemId, getTenantId());
  }


  @RequestMapping(method = RequestMethod.PUT, value = RestConst.URL_UPDATE)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public void update(@RequestBody T resource) {
    updateInternal(resource, getTenantId());
  }


  @RequestMapping(method = RequestMethod.GET, value = RestConst.URL_LIST)
  @ResponseStatus(value = HttpStatus.OK)
  @ResponseBody
  public List<T> findAll() {
    return findAllInternal(getTenantId());
  }


  /*protected String getEntityName() {
    return this.clazz.getSimpleName();
  }*/


  // template method



}