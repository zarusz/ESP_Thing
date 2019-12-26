package com.zarusz.control.repository.jpa;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.repository.DeviceRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
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
    public List<HubDevice> findHubAll() {
        final String q = "select h from HubDevice h left join fetch h.partition";

        List<HubDevice> devices = em
            .createQuery(q, HubDevice.class)
            .getResultList();

        return devices;
    }

    @Override
    public List<Device> findAllInPartition(int partitionId) {
        final String q = "select distinct d from Device d left join fetch d.partition p left join fetch d.features df left join fetch df.feature f where d.partition.id = :partitionId";

        List<Device> devices = em
                .createQuery(q, Device.class)
                .setParameter("partitionId", partitionId)
                .getResultList();

        // initialize all associated endpoint devices
        //devices.stream().filter(x -> x instanceof HubDevice).forEach(d -> Hibernate.initialize(((HubDevice) d).getEndpoints()));

        return devices;
    }

    @Override
    public HubDevice findByGuid(String guid) {
        final String q = "select distinct h from HubDevice h left join fetch h.partition p left join fetch h.features left join fetch h.endpoints e left join fetch e.features where h.guid = :guid";

        HubDevice device = Utils.getSingleResultOrNull(em
            .createQuery(q, HubDevice.class)
            .setParameter("guid", guid));

        return device;
    }

    @Override
    public HubDevice findById(int deviceId) {
        final String q = "select distinct h from HubDevice h left join fetch h.partition p left join fetch h.features left join fetch h.endpoints e left join fetch e.features where h.id = :id";

        HubDevice device = Utils.getSingleResultOrNull(em
            .createQuery(q, HubDevice.class)
            .setParameter("id", deviceId));

        return device;
    }

    @Override
    public Device findOne(Integer id) {
        final String q = "select distinct d from Device d left join fetch d.hub h left join fetch d.features df left join fetch df.feature f where d.id = :id";

        Device device = Utils.getSingleResultOrNull(em
                .createQuery(q, Device.class)
                .setParameter("id", id));

        return device;
    }
}
