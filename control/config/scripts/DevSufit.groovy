/**
 * Created by Tomasz on 31.10.2016.
 */
def device = feature.getDevice().getHub()
if (device == null) {
    device = feature.getDevice()
}

if (device.getGuid() != "dev_sufit") {
    return 0
}

def switchPower = device.getFeatureByPort(16)
def switchLight1 = device.getFeatureByPort(10)
def switchLight2 = device.getFeatureByPort(11)
def switchLight3 = device.getFeatureByPort(12)
def switchLight4 = device.getFeatureByPort(13)
def switchLights = [switchLight1, switchLight2, switchLight3, switchLight4]

if (switchLights.contains(feature) && feature.isOn()) {
    switchPower.setOn(true)
    return 1
}
if (switchLights.contains(feature) && !feature.isOn() && switchLights.grep({ it.isOn() }).isEmpty()) {
    switchPower.setOn(false)
    return 1
}
if (feature == switchPower && !feature.isOn()) {
    switchLights.each({ it.setOn(false) })
    return 1
}
return 0

