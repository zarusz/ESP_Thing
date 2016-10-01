package com.zarusz.control.repository;

import com.zarusz.control.domain.feature.Feature;
import com.zarusz.control.domain.feature.FeatureType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tomasz on 9/27/2016.
 */
public interface FeatureRepository extends JpaRepository<Feature, FeatureType> {

    //Feature findByGuid(String guid);

}
