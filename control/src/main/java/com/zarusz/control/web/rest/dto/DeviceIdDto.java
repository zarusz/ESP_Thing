package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import lombok.Data;

/**
 * Created by Tomasz on 10/2/2016.
 */
@Data
public class DeviceIdDto {

    private int id;

    public DeviceIdDto() {
    }

    public DeviceIdDto(Device device) {
        this.id = device.getId();
    }
}
