package panda.repository;

import panda.domein.entities.Receipt;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class ReceiptRepositoryImpl implements ReceiptRepository {
    private final EntityManager entityManager;

    @Inject
    public ReceiptRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Receipt save(Receipt entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<Receipt> findAll() {

        this.entityManager.getTransaction().begin();
        List<Receipt> packages =
                this.entityManager.createQuery("SELECT u FROM receipts u",Receipt.class)
                        .getResultList();
        this.entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Receipt findById(String id) {
        this.entityManager.getTransaction().begin();
        Receipt receipt = this.entityManager.createQuery("SELECT u FROM receipts u WHERE u.id =: id",Receipt.class)
                .setParameter("id",id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return receipt;
    }

    @Override
    public Long size(){
        this.entityManager.getTransaction().begin();
        Long sizes = this.entityManager
                .createQuery("SELECT count(u) FROM receipts u",Long.class )
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return sizes;
    }


}
