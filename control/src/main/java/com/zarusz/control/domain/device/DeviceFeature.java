package com.zarusz.control.domain.device;

import com.zarusz.control.domain.feature.Feature;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id", "port"})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "feature", discriminatorType = DiscriminatorType.STRING)
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

	@ManyToOne
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_device_feature_device_id"))
	@Setter
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_device_feature_feature_id"), name = "feature", insertable = false, updatable = false)
    @Setter
    private Feature feature;
}
