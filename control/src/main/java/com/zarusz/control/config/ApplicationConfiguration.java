package com.zarusz.control.config;

import com.zarusz.control.app.comm.SwitchFeatureBroker;
import com.zarusz.control.domain.common.EventBus;
import net.engio.mbassy.bus.MBassador;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tomasz on 4/5/2016.
 */
@Configuration
public class ApplicationConfiguration {

    @Bean
    public MBassador bus() {
        /*
        MBassador bus = new MBassador(new BusConfiguration()
            .addFeature(Feature.SyncPubSub.Default())
            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
            .addFeature(Feature.AsynchronousMessageDispatch.Default())
            //.addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
            //.setProperty(IBusConfiguration.Properties.BusId, "global bus")); // this is used for identification in #toString
        */

        MBassador unboundBus = new MBassador();
        EventBus.setInstance(unboundBus);
        return unboundBus;
    }

    @Bean
    public SwitchFeatureBroker switchFeatureBroker(MBassador bus) {
        return new SwitchFeatureBroker(bus);
    }
}
