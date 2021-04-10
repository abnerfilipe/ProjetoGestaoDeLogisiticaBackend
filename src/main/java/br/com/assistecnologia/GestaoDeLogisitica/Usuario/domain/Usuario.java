package br.com.assistecnologia.GestaoDeLogisitica.Usuario.domain;

import br.com.assistecnologia.GestaoDeLogisitica.Funcionario.domain.Funcionario;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String username;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "usuario")
    private Funcionario funcionario;


    public Usuario() {
    }

    public Usuario(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
