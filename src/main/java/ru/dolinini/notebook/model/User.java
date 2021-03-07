package ru.dolinini.notebook.model;

import lombok.Data;
import ru.dolinini.notebook.security.Role;
import ru.dolinini.notebook.security.Status;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NotebookEntry> notes=new HashSet<>();

    @Column(name = "firstname")
    @NotNull
    @NotEmpty(message = "Can't be empty")
    @NotBlank(message = "Can't be only blank space")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9]*$", message = "The first character can't be a digit, can't contain spaces")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String firstname;

    @Column(name = "lastname")
    @NotNull(message="can't be null or empty")
    @Size(min = 2, max = 20, message = "Lastname should be between 2 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9]*$", message = "The first character can't be a digit, can't contain spaces")
    private String lastname;

    @Column(name = "password")
    @NotNull
    @NotEmpty(message = "Can't be empty")
    @NotBlank(message = "Can't be only blank space")
    @Pattern(regexp = "^\\S*$", message = "Can't contain spaces")
    @Size(min = 1, max = 61, message = "Should be between 1 and 61 characters")
    private String password;

    @Column(name = "email")
    @NotNull
    @NotEmpty(message = "Can't be empty")
    @NotBlank(message = "Can't contain blank space")
    @Email(message = "email not valid")
    private String email;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public User() {
    }

    public User(String firstname, String lastname, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.role=Role.USER;
        this.status=Status.ACTIVE;
    }

    public User(String firstname, String password, String email) {
        this.firstname = firstname;
        this.password = password;
        this.email = email;
        this.role=Role.USER;
        this.status=Status.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<NotebookEntry> getNotes() {
        return notes;
    }

    public void setNotes(Set<NotebookEntry> notes) {
        this.notes = notes;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}