package com.zarusz.control.domain.device;

import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.domain.feature.FeatureType;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(of = {"id", "port", "feature"})
@ToString(of = {"id", "port", "feature"})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "feature_type", discriminatorType = DiscriminatorType.STRING)
public abstract class DeviceFeature {

    protected DeviceFeature() {
    }

    public DeviceFeature(Device device, Feature feature, int port) {
        this.device = device;
        this.feature = feature;
        this.port = port;
        this.displayIcon = feature.getDisplayIcon();
        this.displayPriority = feature.getDisplayPriority();
        this.disabled = false;
    }

    @Id
    @GeneratedValue
    private Integer id;
    private int port;
    private Date updated;
    private String displayName;
    private String displayIcon;
    private int displayPriority;
    @Getter
    private boolean disabled;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_device_feature_device_id"))
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "fk_device_feature_feature_id"), name = "feature_type", insertable = false, updatable = false)
    private Feature feature;

    public void disable() {
        disabled = true;
    }

    public void enable() {
        disabled = false;
    }
}
