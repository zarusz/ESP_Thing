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

    protected EndpointDevice() {
    }

    public EndpointDevice(String guid, HubDevice hub) {
        super(guid, hub);
        hub.addEndpoint(this);
    }

}
