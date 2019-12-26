package com.zarusz.control.repository.jpa;

import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.partition.Partition;
import com.zarusz.control.repository.HubDeviceFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomasz on 9/27/2016.
 */
@Repository
public class HubDeviceFactoryImpl extends SimpleJpaRepository<HubDevice, Integer> implements HubDeviceFactory {

    private final EntityManager em;

    @Inject
    public HubDeviceFactoryImpl(EntityManager em) {
        super(HubDevice.class, em);
        this.em = em;
    }

    @Override
    public HubDevice create(String guid) {
        HubDevice hubDevice = new HubDevice(guid);
        em.persist(hubDevice);
        return hubDevice;
    }
}
