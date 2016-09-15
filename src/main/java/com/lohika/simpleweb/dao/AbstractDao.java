package com.lohika.simpleweb.dao;

import com.google.common.base.Strings;
import com.lohika.simpleweb.domain.BasicDbEntity;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractDao<T extends BasicDbEntity> {

  private static final Logger log = Logger.getLogger(AbstractDao.class.getName());

  @Autowired
  ConnectionManager connectionManager;

  public AbstractDao() {
    log.info("Creating a DAO for " + getDomain().getSimpleName());
  }

  public abstract Class<T> getDomain();

  public T get(String id, String tenantId) {
    Query query = createTenantQuery(tenantId);
    Criteria crId = Criteria.where(BasicDbEntity.FIELD_ID).is(id);
    query.addCriteria(crId);
    T res = getTemplate(tenantId).findOne(query, getDomain());
    afterRead(res);
    return res;
  }

  public List<T> list(String tenantId) {
    Query query = createTenantQuery(tenantId);
    List<T> list = getTemplate(tenantId).find(query, getDomain());
    if (list != null) {
      for (T item : list) {
        afterRead(item);
      }
    }
    return list;
  }

  //public abstract List<T> listByIds(List<String> ids, String tenantId);

  public String save(T element, String tenantId) {
    beforeSave(element);
    element.setTenantId(tenantId);
    getTemplate(tenantId).save(element);
    return element.getId();
  }

  public int delete(String id, String tenantId) {
    Query query = createTenantOnlyQuery(tenantId);
    Criteria crId = Criteria.where(BasicDbEntity.FIELD_ID).is(id);
    query.addCriteria(crId);
    WriteResult res = getTemplate(tenantId).remove(query, getDomain());
    return res.getN();
  }

  public int deleteAll(String tenantId) {
    WriteResult res = getTemplate(tenantId).remove(createTenantOnlyQuery(tenantId), getDomain());
    return res.getN();
  }

  public long count(String tenantId) {
    return getTemplate(tenantId).count(createTenantQuery(tenantId), getDomain());
  }

  protected MongoTemplate getTemplate(String tenantId) {
    return connectionManager.getConnection(tenantId);
  }


  protected Query createTenantQuery(String tenantId) {
    Query query = new Query();
    Criteria c = Criteria.where(BasicDbEntity.FIELD_TENANT_ID);
    if (Strings.isNullOrEmpty(tenantId)) {
      c = c.exists(false);
    } else {
      c = c.in(tenantId, null);
    }
    query.addCriteria(c);
    return query;
  }


  protected Query createTenantOnlyQuery(String tenantId) {
    Query query = new Query();
    Criteria c = Criteria.where(BasicDbEntity.FIELD_TENANT_ID).is(tenantId);
    query.addCriteria(c);
    return query;
  }


  protected void beforeSave(T element) {
    // override this method if needed
  }

  protected void afterRead(T element) {
    // override this method if needed
  }

}
