package com.zarusz.control.domain.msg.commands;

import com.zarusz.control.domain.device.Device;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode()
public class UpgradeFirmwareCommand  {

    @Getter
    private Device device;

    @Getter
    private String firmwareUrl;

    public UpgradeFirmwareCommand(Device device, String firmwareUrl) {
        this.device = device;
        this.firmwareUrl = firmwareUrl;
    }
}
