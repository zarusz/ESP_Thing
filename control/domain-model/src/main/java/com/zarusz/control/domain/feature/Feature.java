package com.zarusz.control.domain.feature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Tomasz on 9/9/2015.
 */
@Data
@EqualsAndHashCode(of = {"feature"})
@ToString(of = {"feature", "displayName"})
public class Feature {
    private String feature;
    private String displayName;
    private Integer displayPriority;
}


