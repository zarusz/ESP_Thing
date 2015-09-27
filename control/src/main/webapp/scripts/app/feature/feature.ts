///<reference path="..\app.module.ts"/>
///<reference path="feature.directive.ts"/>
///<reference path="feature.switch.directive.ts"/>
///<reference path="feature.dim.directive.ts"/>
///<reference path="feature.led.directive.ts"/>
module App.Feature {

    $module.directive(FeatureDirective.$name, [() => new FeatureDirective()]);
    $module.directive(FeatureSwitchDirective.$name, [() => new FeatureSwitchDirective()]);
    $module.directive(FeatureDimDirective.$name, [() => new FeatureDimDirective()]);
    $module.directive(FeatureLedDirective.$name, [() => new FeatureLedDirective()]);

}