package com.zarusz.control.web.rest;

import com.zarusz.control.domain.partition.Partition;
import com.zarusz.control.repository.PartitionRepository;
import com.zarusz.control.web.rest.dto.PartitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class PartitionResource {

    private final Logger log = LoggerFactory.getLogger(PartitionResource.class);

    @Inject
    private PartitionRepository partitionRepo;

    @RequestMapping(value = "/partition",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public PartitionDTO getRoot() {

        Optional<Partition> root = partitionRepo.findRootFetchChildren();
        return root
                .map(PartitionDTO::new)
                .orElse(null);
    }
}
