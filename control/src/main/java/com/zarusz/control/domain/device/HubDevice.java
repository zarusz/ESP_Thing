package com.zarusz.control.domain.device;

import lombok.*;
import org.hibernate.FetchMode;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;
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

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "last_online")
    private DateTime lastOnline;

    @Fetch(value = org.hibernate.annotations.FetchMode.SUBSELECT)
    @BatchSize(size = 20)
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "hub", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Device> endpoints;

    @Override
    public HubDevice getHub() {
        return this;
    }

    protected HubDevice() {
    }

    public HubDevice(String guid) {
        super(guid, null);
        this.endpoints = new HashSet<>();
    }

    public Device addEndpoint(EndpointDevice endpoint) {
        endpoints.add(endpoint);
        endpoint.setHub(this);
        return endpoint;
    }

    public void removeEndpoint(EndpointDevice endpoint) {
        endpoints.remove(endpoint);
        endpoint.setHub(null);
    }

    @Override
    public DeviceFeature getFeatureByPort(int port) {
        DeviceFeature f = super.getFeatureByPort(port);
        if (f != null) {
            return f;
        }
        for (Device endpoint : endpoints) {
            f = endpoint.getFeatureByPort(port);
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    @Override
    public DeviceFeature getFeatureById(int featureId) {
        DeviceFeature f = super.getFeatureById(featureId);
        if (f != null) {
            return f;
        }
        for (Device endpoint : endpoints) {
            f = endpoint.getFeatureById(featureId);
            if (f != null) {
                return f;
            }
        }
        return null;
    }

    public void onReportActivity() {
        lastOnline = DateTime.now();
    }
}
