package com.zarusz.control.app.comm;

import com.zarusz.control.domain.msg.commands.SwitchCommand;
import com.zarusz.control.domain.msg.commands.TargetingDeviceCommand;
import lombok.Data;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;

@Data
public class SwitchFeatureBroker extends BaseGatewayBroker {

    public SwitchFeatureBroker(MBassador bus) {
        super(bus, LoggerFactory.getLogger(SwitchFeatureBroker.class));
    }

    @Handler
    public void handleDeviceCommand(TargetingDeviceCommand cmd) {
        // TODO: send to device
        log.debug("Generic device command received.");
    }

    @Handler
    public void handleSwitch(SwitchCommand cmd) {
        // TODO: send to device
        log.debug("Switch to {} on device feature {}.", cmd.isOn() ? "on" : "off", cmd.getDeviceFeature().getId());
    }
}
