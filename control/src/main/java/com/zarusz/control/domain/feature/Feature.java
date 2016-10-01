package com.zarusz.control.domain.feature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by Tomasz on 9/9/2015.
 */
@Data
@EqualsAndHashCode(of = {"feature"})
@ToString(of = {"feature", "displayName"})
@Entity
@BatchSize(size = 20)
public class Feature {
    @Id
    @Enumerated(EnumType.STRING)
    private FeatureType feature;
    private String displayName;
    private String displayIcon;
    private Integer displayPriority;
}


