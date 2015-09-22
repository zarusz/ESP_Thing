package com.zarusz.control.web.rest;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.repository.DeviceRepository;
import com.zarusz.control.web.rest.dto.DeviceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
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

    @RequestMapping(value = "/device",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDTO> getByPartition(@RequestParam("partitionId") int partitionId) {

        List<Device> devices = deviceRepo.findAllInPartition(partitionId);

        return devices
                .stream()
                .map(DeviceDTO::new)
                .collect(Collectors.toList());
    }

}
