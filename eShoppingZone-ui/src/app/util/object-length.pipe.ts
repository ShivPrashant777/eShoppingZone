import { Pipe, PipeTransform } from '@angular/core';
/*
 * Returns the number of elements in the object
 */
@Pipe({ name: 'objectLength' })
export class ObjectLengthPipe implements PipeTransform {
  transform(value: object): number {
    return Object.keys(value).length;
  }
}
