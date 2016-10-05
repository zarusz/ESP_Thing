package com.zarusz.control.app.comm;

import com.zarusz.control.app.comm.base.TopicHandler;
import com.zarusz.control.app.comm.messages.Mappings;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.FeatureType;
import com.zarusz.control.repository.DeviceRepository;
import com.zarusz.control.repository.FeatureRepository;
import com.zarusz.control.repository.HubDeviceFactory;
import net.engio.mbassy.bus.MBassador;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Tomasz on 4/10/2016.
 */
public class DeviceDescriptionHandler extends TopicHandler<DeviceMessageProtos.DeviceDescription> {

    public DeviceDescriptionHandler(MBassador bus) {
        super(bus, LoggerFactory.getLogger(DeviceDescriptionHandler.class), Topics.DeviceDescription);
    }

    @Inject
    private DeviceRepository deviceRepository;
    @Inject
    private HubDeviceFactory deviceFactory;
    @Inject
    private FeatureRepository featureRepository;

    public void handle(String topic, DeviceMessageProtos.DeviceDescription msg) {
        log.debug("Received DeviceDescription for device {}.", msg.getDeviceId());

        HubDevice device = deviceRepository.findByGuid(msg.getDeviceId());
        if (device == null) {
            // create if device wasn't registered
            device = deviceFactory.create(msg.getDeviceId());
        }

        for (DeviceMessageProtos.DevicePort devicePort : msg.getPortsList()) {
            FeatureType portFeatureType = Mappings.mapFeatureType(devicePort.getFeature());

            DeviceFeature deviceFeature = device.getFeatureByPort(devicePort.getPort());
            // check if feature exists on port
            if (deviceFeature != null && !deviceFeature.getFeature().getFeature().equals(portFeatureType)) {
                // remove if feature type doesn't match
                device.removeFeature(deviceFeature);
                deviceFeature = null;
            }

            if (deviceFeature == null) {
                // create new one
                deviceFeature = device.addFeature(devicePort.getPort(), portFeatureType, featureRepository);
                if (devicePort.hasDescription()) {
                    deviceFeature.setDisplayName(devicePort.getDescription());
                }
            } else if (deviceFeature.isDisabled()) {
                deviceFeature.enable();
            }
        }

        final Set<Integer> existingPorts = msg.getPortsList()
            .stream()
            .map(x -> x.getPort())
            .collect(Collectors.toSet());

        // remove non-existing
        device.getFeatures()
            .stream()
            .filter(x -> !existingPorts.contains(x.getPort()))
            .forEach(x -> x.disable());
    }
}
