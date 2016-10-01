package com.zarusz.control.app.comm.base;

import net.engio.mbassy.bus.MBassador;
import org.slf4j.Logger;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Tomasz on 4/5/2016.
 */
public abstract class AbstractHandler implements Closeable {

    protected final Logger log;
    protected final MBassador bus;

    public AbstractHandler(MBassador bus, Logger log) {
        this.log = log;
        this.bus = bus;
        bus.subscribe(this);
    }

    @Override
    public void close() throws IOException {
        bus.unsubscribe(this);
    }
}
