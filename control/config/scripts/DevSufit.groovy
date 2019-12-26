/**
 * Created by Tomasz on 31.10.2016.
 */
def device = feature.getDevice().getHub()
if (device.getGuid() != "dev_sufit") {
    return false
}

def switchPower = device.getFeatureByPort(16)
def switchLight1 = device.getFeatureByPort(10)
def switchLight2 = device.getFeatureByPort(11)
def switchLight3 = device.getFeatureByPort(12)
def switchLight4 = device.getFeatureByPort(13)
def switchLights = [switchLight1, switchLight2, switchLight3, switchLight4]

def affected = false;

if (switchLights.contains(feature)) {
    if (feature.isOn()) {
        switchPower.on()
        affected = true
    }
    if (!feature.isOn() && switchLights.grep({ it.isOn() }).isEmpty()) {
        switchPower.off()
        affected = true
    }
}
if (feature == switchPower) {
    if (!feature.isOn()) {
        switchLights.each { it.off() }
        affected = true
    }
}
return affected

