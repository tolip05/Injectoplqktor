package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.models.service.UserServiceModel;
import panda.domein.models.view.PackageViewModel;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class HomeBean {
    private List<PackageViewModel> pandingPackage;
    private List<PackageViewModel> shippedPackage;
    private List<PackageViewModel> deliveredPackage;
    private UserService userService;
    private ModelMapper modelMapper;

    public HomeBean() {
    }

    @Inject
    public HomeBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.initPackages();
    }

    private void initPackages() {
        UserServiceModel userServiceModel =
                this.userService.findUserByUsername(
                        (String) ((HttpSession) FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSession(true))
                                .getAttribute("username"));
        this.pandingPackage = userServiceModel.getPackages()
                .stream()
                .filter(p -> p.getStatus().name().equals("Pending"))
                .map(p -> this.modelMapper
                        .map(p, PackageViewModel.class))
                .collect(Collectors.toList());

        this.shippedPackage = userServiceModel.getPackages()
                .stream()
                .filter(p -> p.getStatus().name().equals("Shipped"))
                .map(p -> this.modelMapper
                        .map(p, PackageViewModel.class))
                .collect(Collectors.toList());
        this.deliveredPackage = userServiceModel.getPackages()
                .stream()
                .filter(p -> p.getStatus().name().equals("Delivered"))
                .map(p -> this.modelMapper
                        .map(p, PackageViewModel.class))
                .collect(Collectors.toList());

    }

    public List<PackageViewModel> getPackageViewModelList() {
        return this.pandingPackage;
    }

    public void setPackageViewModelList(List<PackageViewModel> packageViewModelList) {
        this.pandingPackage = packageViewModelList;
    }

    public List<PackageViewModel> getShippedPackage() {
        return this.shippedPackage;
    }

    public void setShippedPackage(List<PackageViewModel> shippedPackage) {
        this.shippedPackage = shippedPackage;
    }

    public List<PackageViewModel> getDeliveredPackage() {
        return this.deliveredPackage;
    }

    public void setDeliveredPackage(List<PackageViewModel> deliveredPackage) {
        this.deliveredPackage = deliveredPackage;
    }
}
