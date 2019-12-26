package com.zarusz.control.domain.msg.responses;

import com.zarusz.control.domain.msg.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Created by Tomasz on 9/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConfirmationResponse extends Response {

    @Getter
    private String commandId;

}
