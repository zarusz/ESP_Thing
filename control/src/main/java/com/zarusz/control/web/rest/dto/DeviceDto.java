package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.web.rest.dto.feature.FeatureDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
public class DeviceDto extends DeviceDescDto {
    private List<FeatureDto> features;
    private List<DeviceDto> children;
    private int hubId;

    public DeviceDto() {
    }

    public DeviceDto(Device e) {
        this(e, e.getHub() == null ? e.getId() : e.getHub().getId(), false);
    }

    public DeviceDto(Device e, int hubId, boolean includeDisabledFeatures) {
        super(e);
        this.hubId = hubId;
        this.features = e.getFeatures().stream()
            .filter(x -> includeDisabledFeatures || !x.isDisabled()).map(FeatureDto::new)
            .collect(Collectors.toList());

        this.children = new ArrayList<>();
        if (e instanceof HubDevice) {
            children = ((HubDevice) e).getEndpoints().stream()
                .map(x -> new DeviceDto(x, e.getId(), includeDisabledFeatures))
                .collect(Collectors.toList());
        }
    }
}
