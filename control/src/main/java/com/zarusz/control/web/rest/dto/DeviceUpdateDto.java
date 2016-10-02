package com.zarusz.control.web.rest.dto;

import lombok.Data;

/**
 * Created by Tomasz on 10/2/2016.
 */
@Data
public class DeviceUpdateDto extends DeviceIdDto {

    private String displayName;
    private String displayIcon;
    private PartitionIdDto partition;

}
