package com.zarusz.control.domain.comm;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "connection")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "connection_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("connection")
public class Connection {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "source_id")
	private Hub source;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "target_id")
	private Hub target;

	protected Connection() {
	}

	protected Connection(Hub source, Hub target) {
		this.source = source;
		this.target = target;
	}
	
}
