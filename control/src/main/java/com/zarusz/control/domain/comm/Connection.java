package com.zarusz.control.domain.comm;

import javax.persistence.*;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("connection")
public class Connection {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_connection_source_id"))
	private Hub source;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_connection_target_id"))
	private Hub target;

	protected Connection() {
	}

	protected Connection(Hub source, Hub target) {
		this.source = source;
		this.target = target;
	}
	
}
