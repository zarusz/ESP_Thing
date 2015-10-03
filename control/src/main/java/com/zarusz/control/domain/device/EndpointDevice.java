package com.zarusz.control.domain.device;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

/**
 * Created by Tomasz on 9/8/2015.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("endpoint")
public class EndpointDevice extends Device {

    @BatchSize(size = 20)
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_device_device_id"))
    private HubDevice hub;

    protected EndpointDevice() {
    }

    public EndpointDevice(String guid, HubDevice hub) {
        super(guid);
        this.hub = hub;
        hub.addEndpoint(this);
    }

}
