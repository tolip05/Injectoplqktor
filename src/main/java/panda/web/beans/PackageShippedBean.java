package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.entities.Status;
import panda.domein.models.view.ShippedViewModel;
import panda.service.PackageService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class PackageShippedBean {
    List<ShippedViewModel> shipped;
    List<ShippedViewModel> delivered;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public PackageShippedBean() {
    }

    @Inject
    public PackageShippedBean(PackageService packageService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
        this.initViews();
        this.initDelivered();
    }

    private void initDelivered() {
        this.delivered =
                this.packageService.findAllPackageByStatus(Status.Delivered)
                .stream()
                .map(p -> {
                    ShippedViewModel packageViewModel = this.modelMapper.map(p,ShippedViewModel.class);
                    packageViewModel.setRecipient(p.getRecipient().getUsername());
                    return packageViewModel;
                })
                .collect(Collectors.toList());
    }

    private void initViews() {
        this.shipped =
                this.packageService.findAllPackageByStatus(Status.Shipped)
                        .stream()
                        .map(p -> {
                            ShippedViewModel packageViewModel = this.modelMapper.map(p,ShippedViewModel.class);
                            packageViewModel.setRecipient(p.getRecipient().getUsername());
                            return packageViewModel;
                        })
                        .collect(Collectors.toList());
    }

    public List<ShippedViewModel> getShipped() {
        return this.shipped;
    }

    public void setShipped(List<ShippedViewModel> shipped) {
        this.shipped = shipped;
    }

    public List<ShippedViewModel> getDelivered() {
        return this.delivered;
    }

    public void setDelivered(List<ShippedViewModel> delivered) {
        this.delivered = delivered;
    }

    public void changeStatus(String id) throws IOException {

        this.packageService.packageChangeStatus(id);
        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/shipped.xhtml");
    }
}
