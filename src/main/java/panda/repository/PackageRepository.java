package panda.repository;

import panda.domein.entities.Package;
import panda.domein.entities.Status;
import panda.domein.models.service.PackageServiceModel;

import java.util.List;

public interface PackageRepository extends GenericRepository<Package,String> {
    List<Package> findAllPackagesByStatus(Status status);
    Package updatePackage(Package aPackage);
    Package findByName(String name);

}
