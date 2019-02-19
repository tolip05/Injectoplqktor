package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.entities.Status;
import panda.domein.models.view.PackageViewModel;
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
public class PendingPackegesBean {
    private List<PackageViewModel> packageViewModels;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public PendingPackegesBean() {
    }

    @Inject
    public PendingPackegesBean(PackageService packageService,
                               ModelMapper modelMapper) {
        this.packageService = packageService;
        this.modelMapper = modelMapper;
        this.initPackeges();
    }

    private void initPackeges() {
        this.packageViewModels =
                this.packageService.findAllPackageByStatus(Status.Pending)
                .stream()
                .map(p -> {
                    PackageViewModel packageViewModel = this.modelMapper.map(p,PackageViewModel.class);
                    packageViewModel.setRecipient(p.getRecipient().getUsername());
                    return packageViewModel;
                })
                .collect(Collectors.toList());
    }

    public List<PackageViewModel> getPackageViewModels() {
        return this.packageViewModels;
    }

    public void setPackageViewModels(List<PackageViewModel> packageViewModels) {
        this.packageViewModels = packageViewModels;
    }

    public void changeStatus(String id) throws IOException {

        this.packageService.packageChangeStatus(id);
        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/pending.xhtml");
    }
}
