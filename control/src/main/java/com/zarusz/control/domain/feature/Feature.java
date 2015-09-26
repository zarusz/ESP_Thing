package com.zarusz.control.domain.feature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

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
    private String feature;
    private String displayName;
    private Integer displayPriority;
}


