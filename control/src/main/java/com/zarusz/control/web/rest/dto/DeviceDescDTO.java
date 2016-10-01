package com.zarusz.control.web.rest.dto;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.HubDevice;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by Tomasz on 5/30/2016.
 */
@Data
public class DeviceDescDTO {

    private Integer id;
    private String guid;
    private String type;
    private String displayName;
    private Integer displayPriority;
    private String displayIcon;
    private DateTime lastOnline;
    private PartitionDescDTO partition;

    public DeviceDescDTO() {
    }

    public DeviceDescDTO(Device e) {
        id = e.getId();
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
            partition = new PartitionDescDTO(e.getPartition());
        }
    }
}
