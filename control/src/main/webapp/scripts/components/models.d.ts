// Generated using typescript-generator version 1.11.232 on 2016-10-22 12:26:28.

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

    interface DeviceStatusDto extends DeviceIdDto {
        status: string;
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
        signal: IRSignal;
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

    interface IRSignal {
        format: IRFormat;
        bytes: IRSignalByte[];
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
        millisOfSecond: number;
        secondOfMinute: number;
        monthOfYear: number;
        minuteOfDay: number;
        secondOfDay: number;
        weekyear: number;
        yearOfEra: number;
        millisOfDay: number;
        minuteOfHour: number;
        centuryOfEra: number;
        yearOfCentury: number;
        hourOfDay: number;
    }

    interface Serializable {
    }

    interface IRSignalByte {
        bits: number;
        data: number;
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

    type IRFormat = "NEC" | "SONY";

    type FeatureStateDtoUnion = SwitchFeatureStateDto | IRFeatureStateDto | IRSensorFeatureStateDto | TemperatureSensorFeatureStateDto | HumiditySensorFeatureStateDto | MotionSensorFeatureStateDto;

}
