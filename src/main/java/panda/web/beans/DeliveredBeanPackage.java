package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.entities.Status;
import panda.domein.models.service.PackageServiceModel;
import panda.domein.models.view.PackageViewModel;
import panda.domein.models.view.ShippedViewModel;
import panda.service.PackageService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class DeliveredBeanPackage {
    private List<ShippedViewModel> delivered;
    private PackageViewModel packageViewModel;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public DeliveredBeanPackage() {
    }

    @Inject
    public DeliveredBeanPackage(PackageService packageService, ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;

        this.initDelivered();
    }

    private void initDelivered() {
        this.delivered =
                this.packageService.findAllPackageByStatus(Status.Delivered)
                        .stream()
                        .map(p -> {
                            ShippedViewModel packageViewModel = this.modelMapper.map(p, ShippedViewModel.class);
                            packageViewModel.setRecipient(p.getRecipient().getUsername());
                            return packageViewModel;
                        })
                        .collect(Collectors.toList());
    }


    public List<ShippedViewModel> getDelivered() {
        return this.delivered;
    }

    public void setDelivered(List<ShippedViewModel> delivered) {
        this.delivered = delivered;
    }

    public PackageViewModel getPackageViewModel() {
        return this.packageViewModel;
    }

    public void setPackageViewModel(PackageViewModel packageViewModel) {
        this.packageViewModel = packageViewModel;
    }

    public void getDetails(String id) throws IOException {

        PackageServiceModel model = this.packageService.findById(id);
        this.packageViewModel =
                this.modelMapper.map(model,PackageViewModel.class);
         this.packageViewModel.setRecipient(model.getRecipient().getUsername());
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.setAttribute("recipient",this.packageViewModel.getRecipient());
        httpSession.setAttribute("address",this.packageViewModel.getShippingAddress());
        httpSession.setAttribute("weight",this.packageViewModel.getWeight());
        httpSession.setAttribute("status",this.packageViewModel.getStatus());
        httpSession.setAttribute("dat",this.packageViewModel.getEstimatedDeliveryTime());
        httpSession.setAttribute("description", this.packageViewModel.getDescription());
        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/details.xhtml");
    }
}
