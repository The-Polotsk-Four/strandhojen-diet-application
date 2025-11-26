package dk.polotsk.backend.Catalog.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Userrole userrole;

    private String login;
    private String password;
    private String name;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public Userrole getUserrole() {
        return userrole;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserrole(Userrole userrole) {
        this.userrole = userrole;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}
