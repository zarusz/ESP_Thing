package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.partition.Partition;
import lombok.Data;

/**
 * Created by Tomasz on 10/1/2016.
 */
@Data
public class PartitionDescDTO {

    private Integer id;
    private String displayName;

    public PartitionDescDTO() {
    }

    public PartitionDescDTO(Partition e) {
        id = e.getId();
        displayName = e.getDisplayName();
    }
}
