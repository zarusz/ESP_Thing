package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
public class DeviceDTO {
    private Integer id;
    private String displayName;
    private List<FeatureDTO> features;

    public DeviceDTO() {
    }

    public DeviceDTO(Device e) {
        id = e.getId();
        displayName = e.getDisplayName();
        features = e.getFeatures().stream().map(FeatureDTO::new).collect(Collectors.toList());
    }
}
