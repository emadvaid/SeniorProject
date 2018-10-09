import { ValidatorFn, AbstractControl, NG_VALIDATORS } from '@angular/forms';
import { Directive, Input } from '@angular/core';

@Directive({
    selector: '[appForbiddenName]',
    providers: [{
        provide: NG_VALIDATORS, useExisting: ForbiddenNameValidatorDirective, multi: true
    }]
})
export class ForbiddenNameValidatorDirective {
    @Input('appForbiddenName') appForbiddenName: string;

    validate(control: AbstractControl): {[key: string]: any} {
        return this.appForbiddenName ? forbiddenNameValidator(
            RegExp(this.appForbiddenName, 'i')
        ) : null;
    }
}

export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {
      const forbidden = nameRe.test(control.value);
      return forbidden ? {'forbiddenName': {value: control.value}} : null;
    };
  }

