package com.zarusz.control.repository;

import com.zarusz.control.domain.partition.Partition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Created by Tomasz on 9/14/2015.
 */
@NoRepositoryBean
public interface PartitionRepository extends CrudRepository<Partition, Integer> {

    Optional<Partition> findRootFetchChildren();

}
