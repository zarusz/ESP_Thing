package com.zarusz.control.domain.topology;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zarusz.control.domain.features.DeviceFeature;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "device")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "device_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("device")
public abstract class Device {

	@Id
	@GeneratedValue
	private Integer id;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "device", orphanRemoval = true)
	@Setter(AccessLevel.PROTECTED)
	private Set<DeviceFeature> features = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "partition_id", foreignKey = @ForeignKey(name = "fk_device_partition_id") )
	@Setter(AccessLevel.PACKAGE)
	private Partition partition;

	public void addFeature(DeviceFeature feature) {
		features.add(feature);
		feature.setDevice(this);
	}

}
