package panda.service;

import panda.domein.entities.Status;
import panda.domein.models.service.PackageServiceModel;

import java.util.List;

public interface PackageService {
    public void packageCreate(PackageServiceModel packageServiceModel);
    List<PackageServiceModel> findAllPackageByStatus(Status status);

    void packageChangeStatus(String id);

    PackageServiceModel findById(String id);
}
