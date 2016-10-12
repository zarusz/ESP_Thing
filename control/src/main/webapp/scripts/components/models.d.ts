// Generated using typescript-generator version 1.11.232 on 2016-10-12 22:57:26.

declare module App.Model {

    interface ChangeStateCommandDto {
        deviceId: string;
        featureId: string;
        state: FeatureStateDtoUnion;
    }

    interface DeviceDescDto extends DeviceIdDto {
        guid: string;
        type: string;
        displayName: string;
        displayIcon: string;
        displayPriority: number;
        lastOnline: DateTime;
        partition: PartitionDescDto;
    }

    interface DeviceDto extends DeviceDescDto {
        features: FeatureDto[];
        children: DeviceDto[];
        hubId: number;
    }

    interface DeviceIdDto {
        id: number;
    }

    interface DeviceUpdateDto {
        displayName: string;
        displayIcon: string;
        partition: PartitionIdDto;
    }

    interface DeviceUpgradeDto {
        firmwareUrl: string;
    }

    interface LoggerDto {
        name: string;
        level: string;
    }

    interface PartitionDescDto extends PartitionIdDto {
        displayName: string;
    }

    interface PartitionDto extends PartitionDescDto {
        displayPriority: number;
        children: PartitionDto[];
    }

    interface PartitionIdDto {
        id: number;
    }

    interface UserDto {
        login: string;
        password: string;
        firstName: string;
        lastName: string;
        email: string;
        langKey: string;
        roles: string[];
    }

    interface FeatureDto extends FeatureWithIdStateDto {
        displayName: string;
        displayIcon: string;
        displayPriority: number;
    }

    interface FeatureStateDto {
        type: "HumiditySensorFeatureStateDto" | "IRFeatureStateDto" | "IRSensorFeatureStateDto" | "MotionSensorFeatureStateDto" | "SwitchFeatureStateDto" | "TemperatureSensorFeatureStateDto";
        updated: Date;
    }

    interface FeatureWithIdDto {
        id: number;
        type: FeatureType;
    }

    interface FeatureWithIdStateDto extends FeatureWithIdDto {
        state: FeatureStateDtoUnion;
    }

    interface HumiditySensorFeatureStateDto extends FeatureStateDto {
        type: "HumiditySensorFeatureStateDto";
        humidity: number;
    }

    interface IRFeatureStateDto extends FeatureStateDto {
        type: "IRFeatureStateDto";
        value: number;
    }

    interface IRSensorFeatureStateDto extends FeatureStateDto {
        type: "IRSensorFeatureStateDto";
    }

    interface MotionSensorFeatureStateDto extends FeatureStateDto {
        type: "MotionSensorFeatureStateDto";
    }

    interface SwitchFeatureStateDto extends FeatureStateDto {
        type: "SwitchFeatureStateDto";
        on: boolean;
    }

    interface TemperatureSensorFeatureStateDto extends FeatureStateDto {
        type: "TemperatureSensorFeatureStateDto";
        temperature: number;
    }

    interface DateTime extends BaseDateTime, ReadableDateTime, Serializable {
    }

    interface BaseDateTime extends AbstractDateTime, ReadableDateTime, Serializable {
    }

    interface ReadableDateTime extends ReadableInstant {
        era: number;
        dayOfMonth: number;
        dayOfWeek: number;
        dayOfYear: number;
        year: number;
        weekOfWeekyear: number;
        secondOfMinute: number;
        millisOfSecond: number;
        yearOfEra: number;
        centuryOfEra: number;
        hourOfDay: number;
        millisOfDay: number;
        weekyear: number;
        yearOfCentury: number;
        minuteOfDay: number;
        secondOfDay: number;
        minuteOfHour: number;
        monthOfYear: number;
    }

    interface Serializable {
    }

    interface AbstractDateTime extends AbstractInstant, ReadableDateTime {
    }

    interface ReadableInstant extends Comparable<ReadableInstant> {
        zone: DateTimeZone;
        millis: number;
        chronology: Chronology;
    }

    interface AbstractInstant extends ReadableInstant {
        equalNow: boolean;
        beforeNow: boolean;
        afterNow: boolean;
    }

    interface DateTimeZone extends Serializable {
        id: string;
        fixed: boolean;
    }

    interface Chronology {
        zone: DateTimeZone;
    }

    interface Comparable<T> {
    }

    type FeatureType = "Switch" | "IR" | "SensorIR" | "SensorTemperature" | "SensorHumidity" | "SensorMotion";

    type FeatureStateDtoUnion = SwitchFeatureStateDto | IRFeatureStateDto | IRSensorFeatureStateDto | TemperatureSensorFeatureStateDto | HumiditySensorFeatureStateDto | MotionSensorFeatureStateDto;

}
