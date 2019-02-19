package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.models.binding.PackageCreateBindingModel;
import panda.domein.models.service.PackageServiceModel;
import panda.domein.models.service.ReceiptServiceModel;
import panda.service.PackageService;
import panda.service.ReceiptService;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class PackageCreateBean {
private List<String> users;
private PackageService packageService;
private UserService userService;
private ModelMapper modelMapper;
private PackageCreateBindingModel model;
private ReceiptService receiptService;
private ReceiptServiceModel receiptServiceModel;

    public PackageCreateBean() {
    }

    @Inject
    public PackageCreateBean(PackageService packageService,
                             UserService userService, ModelMapper modelMapper
    ,ReceiptService receiptService) {

        this.packageService = packageService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.receiptService = receiptService;
        this.initUsers();
        this.initModel();
        this.initReceiprtModel();
    }

    private void initReceiprtModel() {
        this.receiptServiceModel = new ReceiptServiceModel();
    }


    private void initUsers() {
      this.users = this.userService
              .findAllUsers()
              .stream()
              .map(u-> u.getUsername())
              .collect(Collectors.toList());
    }
    private void initModel() {
        this.model = new PackageCreateBindingModel();
    }

    public List<String> getUsers() {
        return this.users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public PackageCreateBindingModel getModel() {
        return this.model;
    }

    public void setModel(PackageCreateBindingModel model) {
        this.model = model;
    }

    public void create() throws IOException {
        PackageServiceModel packageServiceModel = this.modelMapper.map(this.model, PackageServiceModel.class);
        packageServiceModel.setRecipient(this.userService
                .findUserByUsername(this.model.getRecipient()));
        this.packageService
                .packageCreate(packageServiceModel);

        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/home.xhtml");
    }
}
