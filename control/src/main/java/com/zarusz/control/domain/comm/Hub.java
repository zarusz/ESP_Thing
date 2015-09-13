package com.zarusz.control.domain.comm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("hub")
public class Hub {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToMany(mappedBy = "source")
	@Setter(AccessLevel.PROTECTED)
	private Set<Connection> outboundConnections = new HashSet<>();

	@OneToMany(mappedBy = "target")
	@Setter(AccessLevel.PROTECTED)
	private Set<Connection> inboundConnections = new HashSet<>();	
	
}
