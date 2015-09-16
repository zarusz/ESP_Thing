package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import lombok.Data;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
public class DeviceDTO {
    private Integer id;
    private String displayName;

    public DeviceDTO() {
    }

    public DeviceDTO(Device e) {
        id = e.getId();
        displayName = e.getDisplayName();
    }
}
