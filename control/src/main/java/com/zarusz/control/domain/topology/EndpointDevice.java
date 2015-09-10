package com.zarusz.control.domain.topology;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ManyToOne;

/**
 * Created by Tomasz on 9/8/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("endpoint")
public class EndpointDevice extends Device {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    private HubDevice hub;

    protected EndpointDevice() {
    }

    public EndpointDevice(String guid, HubDevice hub) {
        super(guid);
        this.hub = hub;
        hub.addEndpoint(this);
    }

}
