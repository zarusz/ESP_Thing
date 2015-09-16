package com.zarusz.control.domain.partition;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.zarusz.control.domain.device.Device;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("partition")
public class Partition {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_partition_parent_id") )
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
