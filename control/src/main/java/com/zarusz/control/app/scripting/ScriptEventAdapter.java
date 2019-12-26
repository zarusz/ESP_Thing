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
import java.util.List;

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
    private ScriptRepository scriptRepository;

    @Inject
    public ScriptEventAdapter(MBassador bus) {
        super(bus, LOG);
    }

    @Handler
    public void onEvent(SwitchChangedEvent event) {

        Binding binding = new Binding();
        binding.setVariable("feature", event.getFeature());
        binding.setVariable("deviceRepository", deviceRepository);

        List<String> scripts = scriptRepository.findAll(ScriptFlag.EVENT_DRIVEN);
        for (String script : scripts) {
            Object affected = null;
            try {
                affected = engine.run(script, binding);
            } catch (ResourceException e) {
                LOG.error("Resource error", e);
            } catch (ScriptException e) {
                LOG.error("Script error", e);
            } catch (RuntimeException e) {
                LOG.error("Runtime error", e);
            }
            LOG.debug("Script {} affected state: {}", script, affected);
        }

    }

}
