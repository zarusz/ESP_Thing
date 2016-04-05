package com.zarusz.control.app.comm;

import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tomasz on 4/5/2016.
 */
public class BaseGatewayBroker implements Closeable {

    protected final Logger log;
    protected final MBassador bus;

    public BaseGatewayBroker(MBassador bus, Logger log) {
        this.log = log;
        this.bus = bus;
        bus.subscribe(this);
    }

    @Override
    public void close() throws IOException {
        bus.unsubscribe(this);
    }


}
