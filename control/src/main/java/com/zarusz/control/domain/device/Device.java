package com.zarusz.control.domain.device;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.domain.partition.Partition;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Data
@EqualsAndHashCode(of = { "id" })
@ToString(of = {"id", "displayName", "guid"})
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
    private Integer displayPriority;
    private String displayIcon;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "device", orphanRemoval = true, fetch = FetchType.EAGER)
    @Setter(AccessLevel.PROTECTED)
    @BatchSize(size = 20)
	private Set<DeviceFeature> features = new HashSet<>();

    @BatchSize(size = 20)
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_device_device_id"))
    @Setter(AccessLevel.PROTECTED)
    private HubDevice hub;

    @ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_device_partition_id"))
	private Partition partition;

    protected Device() {
    }

    public Device(String guid, HubDevice hub) {
        this.guid = guid;
        this.hub = hub;
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

    public DeviceFeature getFeatureByPort(Integer port) {
        for (DeviceFeature deviceFeature : features) {
            if (port.equals(deviceFeature.getPort())) {
                return deviceFeature;
            }
        }
        return null;
    }

    public DeviceFeature getFeatureById(int featureId) {
        for (DeviceFeature deviceFeature : features) {
            if (deviceFeature.getId() == featureId) {
                return deviceFeature;
            }
        }
        return null;
    }

    public Set<DeviceFeature> getFeaturesOfType(Feature feature) {
        Set<DeviceFeature> featuresOfType = features.stream()
                .filter(deviceFeature -> feature.equals(deviceFeature.getFeature()))
                .collect(Collectors.toSet());

        return featuresOfType;
    }

}
