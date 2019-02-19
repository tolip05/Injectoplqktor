package panda.domein.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Table
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String role;
    private List<Package> packages;
    private List<Receipt> receipts;

    public User() {
        this.packages = new ArrayList<>();
        this.receipts = new ArrayList<>();
    }

    @Column(name = "username",nullable = false,unique = true,updatable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password",nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email",nullable = false,unique = true)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "role",nullable = false)
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(targetEntity = Package.class,mappedBy = "recipient")
    public List<Package> getPackages() {
        return this.packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    @OneToMany(targetEntity = Receipt.class,mappedBy = "recipient")
    public List<Receipt> getReceipts() {
        return this.receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }
}
