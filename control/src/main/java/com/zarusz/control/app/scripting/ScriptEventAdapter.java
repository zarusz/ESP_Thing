package com.zarusz.control.app.scripting;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.domain.msg.events.SwitchChangedEvent;
import com.zarusz.control.repository.DeviceRepository;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Tomasz on 31.10.2016.
 */
@Component
public class ScriptEventAdapter extends AbstractHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ScriptEventAdapter.class);

    @Inject
    private GroovyScriptEngine engine;
    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    public ScriptEventAdapter(MBassador bus) {
        super(bus, LOG);
    }

    @Handler
    public void onEvent(SwitchChangedEvent event) {

        Binding binding = new Binding();

        binding.setVariable("feature", event.getFeature());
        binding.setVariable("deviceRepository", deviceRepository);
        Object r = null;
        try {
            r = engine.run("DevSufit.groovy", binding);
        } catch (ResourceException e) {
            LOG.error("Resource error", e);
        } catch (ScriptException e) {
            LOG.error("Script error", e);
        } catch (RuntimeException e) {
            LOG.error("Runtime error", e);
        }

    }

}
