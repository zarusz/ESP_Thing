package com.zarusz.control.domain.features;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Tomasz on 9/9/2015.
 */
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("feature")
public class Feature {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String type;
    private String displayName;
}


