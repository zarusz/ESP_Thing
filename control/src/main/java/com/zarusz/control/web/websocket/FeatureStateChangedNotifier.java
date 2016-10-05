package com.zarusz.control.web.websocket;

import com.zarusz.control.app.comm.base.AbstractHandler;
import com.zarusz.control.domain.msg.events.FeatureStateChangedEvent;
import com.zarusz.control.web.rest.dto.FeatureDTO;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import javax.inject.Inject;

/**
 * Created by Tomasz on 5/7/2016.
 */
public class FeatureStateChangedNotifier extends AbstractHandler {

    @Inject
    private SimpMessageSendingOperations messagingTemplate;

    public FeatureStateChangedNotifier(MBassador bus) {
        super(bus, LoggerFactory.getLogger(FeatureStateChangedNotifier.class));
    }

    @Handler
    public void onFeatueStateChanged(FeatureStateChangedEvent e) {
        FeatureDTO feature = new FeatureDTO(e.getFeature());
        if (feature != null) {
            messagingTemplate.convertAndSend("/topic/feature-state", feature);
        }
    }
}
