package com.zarusz.control.domain.device;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.domain.partition.Partition;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("device")
public class Device {

	@Id
	@GeneratedValue
	private Integer id;
	private String guid;
    private String displayName;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "device", orphanRemoval = true)
    @Setter(AccessLevel.PROTECTED)
	private Set<DeviceFeature> features = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_device_partition_id"))
	private Partition partition;

    protected Device() {
    }

    public Device(String guid) {
        this.guid = guid;
    }

	public DeviceFeature addFeature(Feature feature) {
        DeviceFeature df = new DeviceFeature(this, feature);
        features.add(df);
        return df;
	}

    public void removeFeature(DeviceFeature feature) {
        if (features.remove(feature)) {
           feature.setDevice(null);
        }
    }

    public DeviceFeature getFeatureByPort(String port) {
        for (DeviceFeature deviceFeature : features) {
            if (port.equals(deviceFeature.getPort())) {
                return deviceFeature;
            }
        }
        return null;
    }

    public Set<DeviceFeature> getFeaturesOfType(Feature feature) {
        Set<DeviceFeature> featuresOfType = new HashSet<>();
        for (DeviceFeature deviceFeature : features) {
            if (feature.equals(deviceFeature.getFeature())) {
                featuresOfType.add(deviceFeature);
            }
        }
        return featuresOfType;
    }
}
