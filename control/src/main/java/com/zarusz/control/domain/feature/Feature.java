package com.zarusz.control.domain.feature;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Created by Tomasz on 9/9/2015.
 */
@Data
@EqualsAndHashCode(of = { "feature" })
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "feature", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("feature")
public class Feature {
    @Id
    private String feature;
    private String displayName;
}


