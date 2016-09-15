package com.lohika.simpleweb.service;


import com.lohika.simpleweb.dao.AbstractDao;
import com.lohika.simpleweb.domain.BasicDbEntity;
import com.lohika.simpleweb.exceptions.ValidationBasicException;
import com.lohika.simpleweb.utils.Utils;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractServiceDb<T extends BasicDbEntity> {

  private static final Logger log = Logger.getLogger(AbstractServiceDb.class.getName());

  public abstract Class<T> getDomain();
  public abstract AbstractDao<T> getDao();

  public AbstractServiceDb() {
    log.info("Creating a service for " + getDomain().getSimpleName());
  }

  public T get(String id, String tenantId) {
    Utils.checkNotNullOrEmpty(id);
    return getDao().get(id, tenantId);
  }

  public List<T> list(String tenantId) {
    return getDao().list(tenantId);
  }

  //public List<T> listByIds(List<String> ids, String tenantId) {  }


  public String saveNew(T element, String tenantId) {
    Utils.checkNotNull(element);
    Utils.checkMustNull(element.getId());
    return getDao().save(element, tenantId);
  }


  public String saveExisting(T element, String tenantId) {
    Utils.checkNotNull(element);
    Utils.checkNotNullOrEmpty(element.getId());
    return getDao().save(element, tenantId);
  }


  public int delete(String id, String tenantId) {
    Utils.checkNotNullOrEmpty(id);
    return getDao().delete(id, tenantId);
  }


  public int deleteAll(String tenantId) {
    return getDao().deleteAll(tenantId);
  }


  public long count(String tenantId) {
    return getDao().count(tenantId);
  }


  /**
   * Override if needed special validation
   * @param element
   * @throws ValidationBasicException
   */
  public void validateOnCreate(T element) throws ValidationBasicException {
    Utils.checkNotNull(element);
  }
}
