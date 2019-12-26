package com.zarusz.control.domain;

import com.zarusz.control.domain.device.Device;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Tomasz on 9/17/2015.
 */
public class DeviceTest {

    @Test
    public void devicesWithSameIdAreEqual() {
        // arrange
        Device d1 = new Device("guid1", null);
        Device d2 = new Device("guid1", null);

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
    public void devicesWithDifferentIdAreNotEqual() {
        // arrange
        Device d1 = new Device("guid1", null);
        Device d2 = new Device("guid1", null);

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
