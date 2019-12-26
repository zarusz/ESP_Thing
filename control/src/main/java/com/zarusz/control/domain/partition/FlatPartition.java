package com.zarusz.control.domain.partition;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("flat")
public class FlatPartition extends Partition {

}
