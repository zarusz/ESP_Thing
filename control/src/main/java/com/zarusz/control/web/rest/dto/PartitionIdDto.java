package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.partition.Partition;
import lombok.Data;

@Data
public class PartitionIdDto {

    private Integer id;

    public PartitionIdDto() {
    }

    public PartitionIdDto(Partition e) {
        id = e.getId();
    }
}
