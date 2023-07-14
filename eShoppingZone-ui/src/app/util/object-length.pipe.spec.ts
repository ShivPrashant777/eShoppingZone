import { ObjectLengthPipe } from './object-length.pipe';

describe('ObjectLengthPipe', () => {
  it('should test the pipe', () => {
    let objectLengthPipe = new ObjectLengthPipe();
    expect(
      objectLengthPipe.transform({
        '5': 4.7,
        '4': 4.6,
        '3': 4.2,
      })
    ).toEqual(3);
  });
});
