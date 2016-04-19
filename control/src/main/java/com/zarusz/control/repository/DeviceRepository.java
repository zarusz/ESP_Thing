package com.zarusz.control.repository;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by Tomasz on 9/21/2015.
 */
@NoRepositoryBean
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    List<Device> findAllInPartition(int partitionId);

    HubDevice findHubByGuid(String deviceId);
}
