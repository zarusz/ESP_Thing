package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Tomasz on 10/16/2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceStatusDto extends DeviceIdDto {

    private String status;

    public DeviceStatusDto() {
    }

    public DeviceStatusDto(Device device) {
    }

}
