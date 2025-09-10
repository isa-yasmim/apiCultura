package br.edu.utfpr.apicultura.app.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe base para todas as entidades do sistema.
 * Contém campos comuns como ID e timestamps de criação e atualização.
 * A anotação @MappedSuperclass indica que esta classe não é uma entidade
 * que será mapeada para uma tabela, mas suas propriedades serão herdadas
 * pelas subclasses que são entidades.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Método de callback do JPA.
     * Será executado antes da entidade ser persistida pela primeira vez.
     * Define as datas de criação e atualização.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Método de callback do JPA.
     * Será executado antes de uma entidade existente ser atualizada no banco de dados.
     * Atualiza apenas a data de atualização.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}