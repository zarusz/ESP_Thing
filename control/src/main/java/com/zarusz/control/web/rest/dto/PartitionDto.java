package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.partition.Partition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 9/14/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PartitionDto extends PartitionDescDto {

    private Integer displayPriority;
    private List<PartitionDto> children = new ArrayList<>();

    public PartitionDto() {
    }

    public PartitionDto(Partition e) {
        super(e);
        displayPriority = e.getDisplayPriority();
        children = e.getChildren().stream().map(PartitionDto::new).collect(Collectors.toList());
    }
}
