package com.zarusz.control.domain.topology;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
public class Partition {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	//@JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_partition_parent_id") )
	@Setter(AccessLevel.PROTECTED)
	private Partition parent;

	@OneToMany(mappedBy = "parent")
	@Setter(AccessLevel.PROTECTED)
	private Set<Partition> children = new HashSet<>();

	private String displayName;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "partition")
	@Setter(AccessLevel.PROTECTED)
	private Set<Device> devices = new HashSet<>();

	protected Partition() {
	}

	public Partition(Partition parent) {
		this.parent = parent;
	}

    public Partition addChild() {
        Partition child = new Partition(this);
        children.add(this);
        return child;
    }

    public void removeChild(Partition child) {
        children.remove(child);
    }

	public void addDevice(Device device) {
		devices.add(device);
		device.setPartition(this);
	}

	public void removeDevice(Device device) {
		devices.remove(device);
		device.setPartition(null);
	}

}
