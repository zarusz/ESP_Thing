package com.zarusz.control.domain.feature.ir;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"bits", "data"})
public class IRSignalByte {
    private byte bits;
    private int data;
}

