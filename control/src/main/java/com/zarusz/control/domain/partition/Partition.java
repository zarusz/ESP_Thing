package com.zarusz.control.domain.partition;

import com.zarusz.control.domain.device.Device;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "displayName"})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("partition")
@BatchSize(size = 10)
public class Partition {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_partition_parent_id"))
    @Setter(AccessLevel.PROTECTED)
    private Partition parent;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
    @Setter(AccessLevel.PROTECTED)
    private Set<Partition> children;

    private String displayName;
    private Integer displayPriority;

    @BatchSize(size = 20)
    @OneToMany(mappedBy = "partition", cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Setter(AccessLevel.PROTECTED)
    private Set<Device> devices;

    protected Partition() {
    }

    public Partition(Partition parent) {
        this.parent = parent;
        this.children = new HashSet<>();
        this.devices = new HashSet<>();
    }

    public Partition addChild() {
        Partition child = new Partition(this);
        children.add(this);
        return child;
    }

    public void removeChild(Partition child) {
        children.remove(child);
        child.setParent(null);
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
