package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.partition.Partition;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        children = e.getChildren().stream().map(PartitionDTO::new).collect(Collectors.toList());
        devices = e.getDevices().stream().map(DeviceDTO::new).collect(Collectors.toList());
    }
}
