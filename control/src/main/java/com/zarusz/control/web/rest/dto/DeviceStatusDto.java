package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import lombok.Data;

/**
 * Created by Tomasz on 10/16/2016.
 */
@Data
public class DeviceStatusDto extends DeviceIdDto {

    private String status;

    public DeviceStatusDto() {
    }

    public DeviceStatusDto(Device device) {
    }

}
