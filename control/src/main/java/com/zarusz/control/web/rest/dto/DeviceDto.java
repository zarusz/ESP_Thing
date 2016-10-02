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
public class DeviceDto extends DeviceDescDto {
    private List<FeatureDTO> features;
    private List<DeviceDto> children;

    public DeviceDto() {
    }

    public DeviceDto(Device e) {
        this(e, false);
    }

    public DeviceDto(Device e, boolean includeDisabledFeatures) {
        super(e);
        features = e.getFeatures().stream().filter(x -> includeDisabledFeatures || !x.isDisabled()).map(FeatureDTO::new).collect(Collectors.toList());
        if (e instanceof HubDevice) {
            HubDevice hubDevice = (HubDevice) e;
            children = hubDevice.getEndpoints().stream().map(DeviceDto::new).collect(Collectors.toList());
        } else {
            children = new ArrayList<>();
        }
    }
}
