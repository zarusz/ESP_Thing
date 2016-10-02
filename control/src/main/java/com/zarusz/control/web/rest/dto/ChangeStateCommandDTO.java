package com.zarusz.control.web.rest.dto;

import com.zarusz.control.web.rest.dto.feature.FeatureStateDto;
import lombok.Data;

/**
 * Created by Tomasz on 9/27/2015.
 */
@Data
public class ChangeStateCommandDTO {

    private String deviceId;
    private String featureId;
    private FeatureStateDto state;

}
