package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.partition.Partition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
public class PartitionDTO {

    private Integer id;
    private String displayName;
    private List<PartitionDTO> children = new ArrayList<>();
    private List<DeviceDTO> devices = new ArrayList<>();

    public PartitionDTO() {
    }

    public PartitionDTO(Partition e) {
        id = e.getId();
        displayName = e.getDisplayName();
        for (Partition child : e.getChildren()) {
            children.add(new PartitionDTO(child));
        }

        for (Device device : e.getDevices()) {
            devices.add(new DeviceDTO(device));
        }
    }
}
