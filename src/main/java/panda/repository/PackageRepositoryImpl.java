package panda.repository;

import panda.domein.entities.Package;
import panda.domein.entities.Status;
import panda.domein.models.service.PackageServiceModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class PackageRepositoryImpl implements PackageRepository {
    private final EntityManager entityManager;

    @Inject
    public PackageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Package save(Package entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public List<Package> findAll() {
        this.entityManager.getTransaction().begin();
        List<Package> packages =
                this.entityManager.createQuery("SELECT u FROM package u",Package.class)
                        .getResultList();
        this.entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Package findById(String id) {
        this.entityManager.getTransaction().begin();
        Package packages = this.entityManager.createQuery("SELECT u FROM package u WHERE u.id =: id",Package.class)
                .setParameter("id",id)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Long size() {
        this.entityManager.getTransaction().begin();
        Long sizes = this.entityManager
                .createQuery("SELECT count(u) FROM package u",Long.class )
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return sizes;
    }

    @Override
    public List<Package> findAllPackagesByStatus(Status status) {
        this.entityManager.getTransaction().begin();
        List<Package> packages =
                this.entityManager.createQuery("SELECT u FROM package u WHERE u.status =: status",Package.class)
                        .setParameter("status",status)
                        .getResultList();
        this.entityManager.getTransaction().commit();
        return packages;
    }

    @Override
    public Package updatePackage(Package aPackage) {
        Package second = aPackage;
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(aPackage);

        this.entityManager.getTransaction().commit();

        return aPackage;
    }

    @Override
    public Package findByName(String name) {
        this.entityManager.getTransaction().begin();
        Package packages = this.entityManager.createQuery("SELECT u FROM package u WHERE u.description =: name",Package.class)
                .setParameter("name",name)
                .getSingleResult();
        this.entityManager.getTransaction().commit();
        return packages;
    }


}
