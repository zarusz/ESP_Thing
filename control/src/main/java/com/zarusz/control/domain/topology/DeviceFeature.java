package com.zarusz.control.domain.topology;

import com.zarusz.control.domain.features.Feature;
import com.zarusz.control.domain.topology.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("device_feature")
public class DeviceFeature {

	protected DeviceFeature() {
	}

	public DeviceFeature(Device device, Feature feature) {
		this.device = device;
        this.feature = feature;
	}

	@Id
	@GeneratedValue
	private Integer id;
    private String port;
    //private String state;

	@ManyToOne
	//@JoinColumn(name = "device_id", foreignKey = @ForeignKey(name = "fk_device_feature_device_id") )
	@Setter
    private Device device;

    @ManyToOne
    //@JoinColumn(name = "feature_id", foreignKey = @ForeignKey(name = "fk_device_feature_feature_id") )
    @Setter
    private Feature feature;

}
