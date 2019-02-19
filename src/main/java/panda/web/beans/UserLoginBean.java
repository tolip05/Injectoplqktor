package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domein.models.binding.UserLoginBindingModel;
import panda.domein.models.service.UserServiceModel;
import panda.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScoped
public class UserLoginBean {
    private UserLoginBindingModel userLoginBindingModel;
    private UserService userService;
    private ModelMapper modelMapper;

    public UserLoginBean() {
    }

    @Inject
    public UserLoginBean(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userLoginBindingModel = new UserLoginBindingModel();
    }

    public UserLoginBindingModel getUserLoginBindingModel() {
        return this.userLoginBindingModel;
    }

    public void setUserLoginBindingModel(UserLoginBindingModel userLoginBindingModel) {
        this.userLoginBindingModel = userLoginBindingModel;
    }

    public void login() throws IOException {
        UserServiceModel userServiceModel =
                this.userService.userLogin(this.modelMapper
                        .map(this.userLoginBindingModel, UserServiceModel.class));
        if (userServiceModel == null) {
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("/faces/jsf/login.xhtml");
            return;
        }
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.setAttribute("username", userServiceModel.getUsername());
        httpSession.setAttribute("role", userServiceModel.getRole());
        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/home.xhtml");
    }
}
