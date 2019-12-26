package com.zarusz.control.domain.feature.ir;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(of = {"format", "bytes"})
public class IRSignal {
    private IRFormat format;
    private List<IRSignalByte> bytes;
}
