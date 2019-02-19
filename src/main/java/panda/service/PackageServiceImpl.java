package panda.service;

import org.modelmapper.ModelMapper;
import panda.domein.entities.Package;
import panda.domein.entities.Status;
import panda.domein.models.service.PackageServiceModel;
import panda.domein.models.service.ReceiptServiceModel;
import panda.domein.models.service.UserServiceModel;
import panda.repository.PackageRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PackageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;
    private final ModelMapper modelMapper;
    private final ReceiptService receiptService;

    @Inject
    public PackageServiceImpl(PackageRepository packageRepository, ModelMapper modelMapper, ReceiptService receiptService) {
        this.packageRepository = packageRepository;
        this.modelMapper = modelMapper;
        this.receiptService = receiptService;
    }

    @Override
    public void packageCreate(PackageServiceModel packageServiceModel) {
        Package aPackage = this.modelMapper.map(packageServiceModel, Package.class);
     aPackage.setStatus(Status.Pending);

        this.packageRepository
             .save(aPackage);
        ReceiptServiceModel receiptServiceModel= this.setReceipt(aPackage.getDescription());
                this.receiptService.packageCreate(receiptServiceModel);


    }

    private ReceiptServiceModel setReceipt(String description) {
        ReceiptServiceModel receiptServiceModel = new ReceiptServiceModel();
        Package aPackage = this.packageRepository.findByName(description);
        receiptServiceModel.setaPackage(this.modelMapper.map(aPackage,PackageServiceModel.class));
        receiptServiceModel.setRecipient(this.modelMapper.map(aPackage.getRecipient(), UserServiceModel.class));
        receiptServiceModel.setFee(BigDecimal.valueOf(aPackage.getWeight() * 3));
        receiptServiceModel.setIssuedOn(LocalDateTime.now());
        return receiptServiceModel;
    }


    @Override
    public List<PackageServiceModel> findAllPackageByStatus(Status status) {
        return this.packageRepository.findAllPackagesByStatus(status)
                .stream().map(p -> this.modelMapper.map(p,PackageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void packageChangeStatus(String id) {
        Package aPackage = this.packageRepository.findById(id);
        this.changeStatus(aPackage);
        this.changeDeliveryDate(aPackage);
        this.packageRepository.updatePackage(aPackage);

    }

    @Override
    public PackageServiceModel findById(String id) {
        return this.modelMapper
                .map(this.packageRepository.findById(id),PackageServiceModel.class);
    }

    private void changeDeliveryDate(Package aPackage) {
        long days = (System.currentTimeMillis() % 21) + 20;
        aPackage.setEstimatedDeliveryTime(LocalDateTime.now().plusDays(days));
    }

    private void changeStatus(Package aPackage){

        aPackage.setStatus(Status.values()[Arrays.asList(Status.values()).indexOf(aPackage.getStatus()) + 1]);
    }
}
