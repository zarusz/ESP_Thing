package com.zarusz.control.domain.device;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tomasz on 9/8/2015.
 */
@Setter
@Getter
@Entity
@DiscriminatorValue("hub")
public class HubDevice extends Device {

    private String address;

    @BatchSize(size = 20)
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "hub", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<EndpointDevice> endpoints;

    protected HubDevice() {
    }

    public HubDevice(String guid, String address) {
        super(guid);
        this.address = address;
        this.endpoints = new HashSet<>();
    }

    public EndpointDevice addEndpoint(EndpointDevice endpoint) {
        endpoints.add(endpoint);
        endpoint.setHub(this);
        return endpoint;
    }

    public void removeEndpoint(EndpointDevice endpoint) {
        endpoints.remove(endpoint);
        endpoint.setHub(null);
    }
}
