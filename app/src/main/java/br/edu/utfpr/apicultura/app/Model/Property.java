package br.edu.utfpr.apicultura.app.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name= "TB_Property")
public class Property extends BaseEntity {
    
    @Column(nullable=false)
    private String address;

    @Column(nullable=false, length = 10)
    private String number;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "property")
    private List<Hive> hives;
}