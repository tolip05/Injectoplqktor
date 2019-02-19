package panda.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScoped
public class UserLogoutBean {
    public void logout() throws IOException {


        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        httpSession.invalidate();
        FacesContext.getCurrentInstance()
                .getExternalContext().redirect("/faces/jsf/index.xhtml");
    }
}
