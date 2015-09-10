package com.zarusz.control.domain.topology;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.zarusz.control.domain.features.Feature;
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
    private String type;
	private String guid;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "device", orphanRemoval = true)
    @Setter(AccessLevel.PROTECTED)
	private Set<DeviceFeature> features = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.PERSIST })
	//@JoinColumn(name = "partition_id", foreignKey = @ForeignKey(name = "fk_device_partition_id"))
	@Setter(AccessLevel.PACKAGE)
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
