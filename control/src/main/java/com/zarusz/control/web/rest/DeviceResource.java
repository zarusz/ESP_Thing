package com.zarusz.control.web.rest;

import com.zarusz.control.app.comm.Topics;
import com.zarusz.control.app.comm.messages.PublishMessageCommand;
import com.zarusz.control.device.messages.DeviceMessageProtos;
import com.zarusz.control.domain.common.EventBus;
import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.DeviceFeature;
import com.zarusz.control.domain.device.HubDevice;
import com.zarusz.control.domain.partition.Partition;
import com.zarusz.control.repository.DeviceRepository;
import com.zarusz.control.repository.PartitionRepository;
import com.zarusz.control.web.rest.dto.DeviceDescDto;
import com.zarusz.control.web.rest.dto.DeviceDto;
import com.zarusz.control.web.rest.dto.DeviceUpdateDto;
import com.zarusz.control.web.rest.dto.DeviceUpgradeDto;
import com.zarusz.control.web.rest.dto.feature.FeatureStateDto;
import com.zarusz.control.web.rest.util.BadRequestException;
import com.zarusz.control.web.rest.util.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
    @Inject
    private PartitionRepository partitionRepo;
    @Inject
    private Topics topics;

    @RequestMapping(value = "/device/status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDescDto> getByPartition() {

        List<HubDevice> devices = deviceRepo.findHubAll();

        return devices
            .stream()
            .map(DeviceDescDto::new)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/device", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDto> getByPartition(@RequestParam("partitionId") int partitionId) {

        List<Device> devices = deviceRepo.findAllInPartition(partitionId);

        return devices
            .stream()
            .map(DeviceDto::new)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/device/{hubId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceDto getById(@PathVariable("hubId") int deviceId) {

        Device device = loadDevice(deviceId);
        return new DeviceDto(device);
    }

    @Transactional
    @RequestMapping(value = "/device/{hubId}", method = RequestMethod.POST)
    public void updateById(@PathVariable("hubId") int deviceId,
                           @RequestBody DeviceUpdateDto deviceDto) {

        Device device = loadDevice(deviceId);

        Partition partition = partitionRepo.findOne(deviceDto.getPartition().getId());
        if (partition == null) {
            throw new BadRequestException("Partition could not be found");
        }

        device.setDisplayName(deviceDto.getDisplayName());
        device.setDisplayIcon(deviceDto.getDisplayIcon());
        device.setPartition(partition);
    }


    @RequestMapping(value = "/device/{hubId}/upgrade", method = RequestMethod.POST)
    public void upgradeById(@PathVariable("hubId") int deviceId,
                           @RequestBody DeviceUpgradeDto deviceDto) {

        Device device = loadDevice(deviceId);

        device.upgradeFrom(deviceDto.getFirmwareUrl());
    }

    private Device loadDevice(@PathVariable("hubId") int deviceId) {
        Device device = deviceRepo.findById(deviceId);
        if (device == null) {
            throw new ResourceNotFoundException();
        }
        return device;
    }

    @RequestMapping(value = "/device/{hubId}/feature/{featureId}/state", method = RequestMethod.POST)
    @Transactional
    public void changeFeatureState(@PathVariable("hubId") int deviceId,
                                   @PathVariable("featureId") int featureId,
                                   @RequestBody FeatureStateDto state) {

        Device device = loadDevice(deviceId);

        DeviceFeature feature = device.getFeatureById(featureId);
        if (feature == null) {
            throw new BadRequestException("Feature could not be found");
        }

        state.handle(feature);
    }

    @RequestMapping(value = "/device/{hubId}/status", method = RequestMethod.GET)
    @Transactional
    public void requestStatus(@PathVariable("hubId") int deviceId) {

        Device device = loadDevice(deviceId);

        DeviceMessageProtos.DeviceStatusRequest r = DeviceMessageProtos.DeviceStatusRequest
            .newBuilder()
            .setReplyTo(topics.getMe())
            .build();

        DeviceMessageProtos.DeviceServiceCommand c = DeviceMessageProtos.DeviceServiceCommand
            .newBuilder()
            .setStatusRequest(r)
            .build();


        EventBus.current().publish(new PublishMessageCommand<>(topics.getDeviceServiceTopic(device), c));
    }

}