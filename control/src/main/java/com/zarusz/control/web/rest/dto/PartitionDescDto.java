package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.partition.Partition;
import lombok.Data;

/**
 * Created by Tomasz on 10/1/2016.
 */
@Data
public class PartitionDescDto extends PartitionIdDto {

    private String displayName;

    public PartitionDescDto() {
    }

    public PartitionDescDto(Partition e) {
        super(e);
        displayName = e.getDisplayName();
    }
}

