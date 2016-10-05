package com.zarusz.control.repository;

import com.zarusz.control.domain.device.HubDevice;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Tomasz on 9/27/2016.
 */
@NoRepositoryBean
public interface HubDeviceFactory {

    HubDevice create(String deviceId);

}
