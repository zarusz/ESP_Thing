package com.zarusz.control.domain;

import com.zarusz.control.domain.device.Device;
import com.zarusz.control.domain.device.EndpointDevice;
import com.zarusz.control.domain.device.HubDevice;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomasz on 9/17/2015.
 */
public class EndpointDeviceTest {

    @Test
    public void endpointDevicesWithSameIdAreEqual() {
        // arrange
        HubDevice hub = new HubDevice("hub1");

        EndpointDevice d1 = new EndpointDevice("guid1", hub);
        EndpointDevice d2 = new EndpointDevice("guid1", hub);

        d1.setId(10);
        d2.setId(10);

        // act
        boolean equalsResult = d1.equals(d2);
        int h1 = d1.hashCode();
        int h2 = d2.hashCode();

        // assert
        assertThat(equalsResult).isTrue();
        assertThat(h1).isEqualTo(h2);
    }

    @Test
    public void endpointDevicesWithDifferentIdAreNotEqual() {
        // arrange
        HubDevice hub = new HubDevice("hub1");

        EndpointDevice d1 = new EndpointDevice("guid1", hub);
        EndpointDevice d2 = new EndpointDevice("guid1", hub);

        d1.setId(11);
        d2.setId(10);

        // act
        boolean equalsResult = d1.equals(d2);
        int h1 = d1.hashCode();
        int h2 = d2.hashCode();

        // assert
        assertThat(equalsResult).isFalse();
        assertThat(h1).isNotEqualTo(h2);
    }


}
