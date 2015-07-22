package com.zarusz.control.domain.impl;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.zarusz.control.domain.comm.Connection;
import com.zarusz.control.domain.comm.Hub;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {}, callSuper = true)
@Entity
@DiscriminatorValue("rf_hub")
public class RFHub extends Hub {

	
	
}
