package com.zarusz.control.domain.device;

import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.feature.*;
import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.commands.UpgradeFirmwareCommand;
import com.zarusz.control.domain.partition.Partition;
import com.zarusz.control.repository.FeatureRepository;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        this.displayName = guid;
        this.displayIcon = "fa-cubes";
        this.hub = hub;
    }

	public DeviceFeature addFeature(int port, FeatureType featureType, FeatureRepository featureRepository) {
        Feature feature = featureRepository.findOne(featureType);
        DeviceFeature df;
        switch (featureType) {
            case Switch:
                df = new SwitchFeature(this, feature, port);
                break;
            case IR:
                df = new IRFeature(this, feature, port);
                break;
            case SensorIR:
                df = new IRSensorFeature(this, feature, port);
                break;
            case SensorTemperature:
                df = new TemperatureSensorFeature(this, feature, port);
                break;
            case SensorHumidity:
                df = new HumiditySensorFeature(this, feature, port);
                break;
            case SensorMotion:
                df = new MotionSensorFeature(this, feature, port);
                break;
            default:
                throw new RuntimeException("Feature type not supported.");
        }

        features.add(df);
        return df;
	}

    public void removeFeature(DeviceFeature feature) {
        if (features.remove(feature)) {
           feature.setDevice(null);
        }
    }

    public DeviceFeature getFeatureByPort(int port) {
        for (DeviceFeature deviceFeature : features) {
            if (port == deviceFeature.getPort()) {
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

    public void upgradeFrom(String firmwareUrl) {
        EventBus.current().publish(new UpgradeFirmwareCommand(this, firmwareUrl));
    }
}
