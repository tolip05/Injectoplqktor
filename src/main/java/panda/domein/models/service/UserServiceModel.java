package panda.domein.models.service;

import java.util.List;

public class UserServiceModel {
    private String id;
    private String username;
    private String password;
    private String email;
    private String role;
    private List<PackageServiceModel> packages;

    public UserServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PackageServiceModel> getPackages() {
        return this.packages;
    }

    public void setPackages(List<PackageServiceModel> packages) {
        this.packages = packages;
    }
}
