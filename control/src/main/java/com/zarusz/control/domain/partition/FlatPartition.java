package com.zarusz.control.domain.partition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("flat")
public class FlatPartition extends Partition {

}
