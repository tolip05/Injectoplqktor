package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.models.service.PackageServiceModel;
import panda.domein.models.service.UserServiceModel;
import panda.domein.models.view.ReceiptViewModel;
import panda.service.PackageService;
import panda.service.ReceiptService;
import panda.service.UserService;

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
public class ReceiptsBean {
    private List<ReceiptViewModel> receiptViewModels;
    private ReceiptViewModel receiptViewModel;
    private ReceiptService receiptService;
    private UserService userService;
    private PackageService packageService;
    private ModelMapper modelMapper;

    public ReceiptsBean() {
    }

    @Inject
    public ReceiptsBean(ReceiptService receiptService,UserService userService,
                        PackageService packageService,
                        ModelMapper modelMapper) {
        this.receiptService = receiptService;
        this.userService = userService;
        this.packageService = packageService;
        this.modelMapper = modelMapper;
        this.initReceipts();
    }

    private void initReceipts() {
        UserServiceModel userServiceModel =
                this.userService.findUserByUsername(
                        (String) ((HttpSession) FacesContext
                                .getCurrentInstance()
                                .getExternalContext()
                                .getSession(true))
                                .getAttribute("username"));
        this.receiptViewModels =
                this.receiptService.getAllReceipts()
                .stream()
                        .filter(p -> p.getRecipient().getId().equals(userServiceModel.getId())).map(r->{
                    ReceiptViewModel receiptViewModel =
                            this.modelMapper
                                    .map(r,ReceiptViewModel.class);
                    receiptViewModel.setRecipient(userServiceModel.getUsername());
                    return receiptViewModel;
                }).collect(Collectors.toList());
    }

    public List<ReceiptViewModel> getReceiptViewModels() {
        return this.receiptViewModels;
    }

    public void setReceiptViewModels(List<ReceiptViewModel> receiptViewModels) {
        this.receiptViewModels = receiptViewModels;
    }

    public ReceiptViewModel getReceiptViewModel() {
        return this.receiptViewModel;
    }

    public void setReceiptViewModel(ReceiptViewModel receiptViewModel) {
        this.receiptViewModel = receiptViewModel;
    }

    public void getDetails(String id) throws IOException {



        this.receiptViewModel = this.modelMapper
                .map(this.receiptService.findById(id),ReceiptViewModel.class);

        String aPackageId = this.receiptService.findById(id).getaPackage().getId();
        PackageServiceModel packageServiceModel
                = this.packageService.findById(aPackageId);
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.setAttribute("id",this.receiptViewModel.getId());
        httpSession.setAttribute("date",this.receiptViewModel.getIssuedOn());
        httpSession.setAttribute("address",packageServiceModel.getShippingAddress());
        httpSession.setAttribute("weight",packageServiceModel.getWeight());
        httpSession.setAttribute("description",packageServiceModel.getDescription());
        httpSession.setAttribute("recipient",packageServiceModel.getRecipient().getUsername());
        httpSession.setAttribute("total",this.receiptViewModel.getFee());

        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/details-receipt.xhtml");
    }
}
