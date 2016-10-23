package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;

/**
 * Created by Tomasz on 5/30/2016.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceDescDto extends DeviceIdDto {

    private String guid;
    private String type;
    private String displayName;
    private String displayIcon;
    private Integer displayPriority;
    private DateTime lastOnline;
    private PartitionDescDto partition;

    public DeviceDescDto() {
    }

    public DeviceDescDto(Device e) {
        super(e);
        guid = e.getGuid();
        type = e instanceof HubDevice ? "hub" : "device";
        displayName = e.getDisplayName();
        displayPriority = e.getDisplayPriority();
        displayIcon = e.getDisplayIcon();
        if (e instanceof HubDevice) {
            HubDevice hubDevice = (HubDevice) e;
            lastOnline = hubDevice.getLastOnline();
        }
        if (e.getPartition() != null) {
            partition = new PartitionDescDto(e.getPartition());
        }
    }
}
