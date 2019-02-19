package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.models.service.PackageServiceModel;
import panda.domein.models.service.UserServiceModel;
import panda.domein.models.view.PackageViewModel;
import panda.service.PackageService;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScoped
public class PendingDetailBean {
    private PackageViewModel packageViewModel;
    private UserService userService;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public PendingDetailBean() {
    }

    @Inject
    public PendingDetailBean(UserService userService, PackageService packageService, ModelMapper modelMapper) {
        this.userService = userService;
        this.packageService = packageService;
        this.modelMapper = modelMapper;
    }


    public PackageViewModel getPackageViewModel() {
        return this.packageViewModel;
    }

    public void setPackageViewModel(PackageViewModel packageViewModel) {
        this.packageViewModel = packageViewModel;
    }

    public void getDetails(String description,String status) throws IOException {
        UserServiceModel userServiceModel =
                this.userService.findUserByUsername(
                        (String) ((HttpSession) FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSession(true))
                                .getAttribute("username"));
        this.packageViewModel =
                userServiceModel.getPackages()
                        .stream()
                        .filter(pa -> pa.getStatus().name().equals(status))
                        .filter(p -> p.getDescription().equals(description))
                        .map(p -> this.modelMapper
                                .map(p, PackageViewModel.class))
                .findFirst().orElse(null);
           this.packageViewModel.setRecipient(userServiceModel.getUsername());

           this.mapModelsView();

        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/details.xhtml");
    }


    private void mapModelsView(){
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.setAttribute("recipient",this.packageViewModel.getRecipient());
        httpSession.setAttribute("address",this.packageViewModel.getShippingAddress());
        httpSession.setAttribute("weight",this.packageViewModel.getWeight());
        httpSession.setAttribute("status",this.packageViewModel.getStatus());
        httpSession.setAttribute("date",this.packageViewModel.getEstimatedDeliveryTime());
        httpSession.setAttribute("description", this.packageViewModel.getDescription());

    }
}
