package com.yahoo.elide.datastores.hibernate5;

import com.yahoo.elide.core.DataStore;
import com.yahoo.elide.core.DataStoreTransaction;
import com.yahoo.elide.core.EntityDictionary;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.Session;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

public class HibernateRevisionsDataStore extends HibernateEntityManagerStore {


    public HibernateRevisionsDataStore(HibernateEntityManager entityManager) {
        super(entityManager, false, ScrollMode.SCROLL_SENSITIVE);
    }

    @Override
    @SuppressWarnings("resource")
    public DataStoreTransaction beginTransaction() {
        Session session = getSession();
        session.setFlushMode(FlushMode.MANUAL);
        return new HibernateRevisionsTransaction(AuditReaderFactory.get(entityManager), session);
    }

    @Override
    public void populateEntityDictionary(EntityDictionary dictionary) {
        for (ClassMetadata meta : entityManager.getSession().getSessionFactory().getAllClassMetadata().values()) {
            if (meta.getMappedClass().getAnnotation(Entity.class) != null) {
                dictionary.bindEntity(meta.getMappedClass());
            }
        }
    }
}
