package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
public class DeviceDTO extends DeviceDescDTO {
    private List<FeatureDTO> features;
    private List<DeviceDTO> children;

    public DeviceDTO() {
    }

    public DeviceDTO(Device e) {
        super(e);
        features = e.getFeatures().stream().map(FeatureDTO::new).collect(Collectors.toList());
        if (e instanceof HubDevice) {
            HubDevice hubDevice = (HubDevice) e;
            children = hubDevice.getEndpoints().stream().map(DeviceDTO::new).collect(Collectors.toList());
        } else {
            children = new ArrayList<>();
        }
    }
}
