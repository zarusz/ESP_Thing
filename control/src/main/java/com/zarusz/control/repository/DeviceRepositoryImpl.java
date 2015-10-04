package com.zarusz.control.repository;

import com.zarusz.control.domain.comm.Hub;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.EndpointDevice;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.partition.Partition;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tomasz on 9/21/2015.
 */
@Repository
public class DeviceRepositoryImpl extends SimpleJpaRepository<Device, Integer> implements DeviceRepository {

    private final EntityManager em;

    @Inject
    public DeviceRepositoryImpl(EntityManager em) {
        super(Device.class, em);
        this.em = em;
    }

    @Override
    public List<Device> findAllInPartition(int partitionId) {
        final String q = "select distinct d from Device d left join fetch d.features df left join fetch df.feature f where d.partition.id = :partitionId";

        List<Device> devices = em
                .createQuery(q, Device.class)
                .setParameter("partitionId", partitionId)
                .getResultList();

        // initialize all associated endpoint devices
        //devices.stream().filter(x -> x instanceof HubDevice).forEach(d -> Hibernate.initialize(((HubDevice) d).getEndpoints()));

        return devices;
    }

    @Override
    public Device findOne(Integer id) {
        final String q = "select distinct d from Device d left join fetch d.features df left join fetch df.feature f where d.id = :id";

        Device device = em
                .createQuery(q, Device.class)
                .setParameter("id", id)
                .getSingleResult();

        return device;
    }
}
