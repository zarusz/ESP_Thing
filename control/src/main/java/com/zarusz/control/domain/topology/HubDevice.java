package com.zarusz.control.domain.topology;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tomasz on 9/8/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("hub")
public class HubDevice extends Device {

    private String address;

    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "hub", orphanRemoval = true)
    private Set<EndpointDevice> endpoints = new HashSet<>();

    protected HubDevice() {
    }

    public HubDevice(String guid, String address) {
        super(guid);
        this.address = address;
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
