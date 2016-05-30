package com.zarusz.control.web.rest;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.repository.DeviceRepository;
import com.zarusz.control.web.rest.dto.DeviceDTO;
import com.zarusz.control.web.rest.dto.DeviceDescDTO;
import com.zarusz.control.web.rest.dto.feature.FeatureStateDTO;
import com.zarusz.control.web.rest.util.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

    @Inject
    private DeviceRepository deviceRepo;

    @RequestMapping(value = "/device/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDescDTO> getByPartition() {

        List<HubDevice> devices = deviceRepo.findHubAll();

        return devices
            .stream()
            .map(DeviceDescDTO::new)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/device", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDTO> getByPartition(@RequestParam("partitionId") int partitionId) {

        List<Device> devices = deviceRepo.findAllInPartition(partitionId);

        return devices
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/device/{deviceId}/feature/{featureId}/state", method = RequestMethod.POST)
    @Transactional
    public void changeFeatureState(@PathVariable("deviceId") int deviceId,
                                   @PathVariable("featureId") int featureId,
                                   @RequestBody FeatureStateDTO state) {

        Device device = deviceRepo.findOne(deviceId);
        if (device != null) {
            DeviceFeature feature = device.getFeatureById(featureId);
            if (feature != null) {
                state.handle(feature);
                return;
            }
        }

        throw new ResourceNotFoundException();
    }

}
