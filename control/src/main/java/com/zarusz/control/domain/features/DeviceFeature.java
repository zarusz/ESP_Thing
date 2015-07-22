package com.zarusz.control.domain.features;

import javax.persistence.Column;
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
import javax.persistence.Table;

import com.zarusz.control.domain.topology.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = { "featureId" })
@Entity
@Table(name = "device_feature")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "feature_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("feature")
public class DeviceFeature {

	protected DeviceFeature() {
	}
	
	public DeviceFeature(Device device) {
		this.device = device;
		device.addFeature(this);
	}

	@Id
	@GeneratedValue
	@Column(name = "feature_id")
	private Integer featureId;

	@Column(name = "feature_name")
	private String featureName;

	@ManyToOne
	@JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_device_feature_device_id") )
	private Device device;

}
