import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ObjectLengthPipe } from 'src/app/util/object-length.pipe';

import { ProductPageComponent } from './product-page.component';

describe('ProductPageComponent', () => {
  let component: ProductPageComponent;
  let fixture: ComponentFixture<ProductPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, AppRoutingModule],
      declarations: [ProductPageComponent, ObjectLengthPipe],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check the loading spinner', () => {
    let fixture = TestBed.createComponent(ProductPageComponent);
    let app = fixture.debugElement.componentInstance;
    app.isLoading = true;
    fixture.detectChanges();
    let compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.sr-only').textContent).toEqual(
      'Loading...'
    );
  });

  it('should display the product name details', () => {
    let fixture = TestBed.createComponent(ProductPageComponent);
    let app = fixture.debugElement.componentInstance;
    app.isLoading = false;
    app.product = {
      productType: 'Wearable',
      productName: 'Apple Watch',
      category: 'Smartwatches',
      rating: {
        '5': 4.7,
        '4': 4.6,
        '3': 4.2,
      },
      review: {
        '1': 'Excellent smartwatch with a sleek design.',
        '2': 'Battery life could be better, but overall a great device.',
        '3': 'Impressive features and seamless integration with iPhone.',
      },
      image:
        'https://firebasestorage.googleapis.com/v0/b/eshoppingzone-7abef.appspot.com/o/images%2Fapplewatch.jpg?alt=media&token=f292c16f-10ec-4f04-84c7-8230c84aa7f6',
      price: 77999.0,
      description:
        'The Apple Watch, a powerful and versatile smartwatch that combines style and functionality.',
      specification: {
        Display: 'Retina LTPO OLED',
        Processor: 'Apple S6',
        Storage: '32GB',
        Connectivity: 'Bluetooth, Wi-Fi',
        Compatibility: 'iOS',
      },
    };
    fixture.detectChanges();
    let compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('#productName').textContent).toEqual(
      'Apple Watch'
    );
    expect(compiled.querySelector('#productImage').src).toEqual(
      'https://firebasestorage.googleapis.com/v0/b/eshoppingzone-7abef.appspot.com/o/images%2Fapplewatch.jpg?alt=media&token=f292c16f-10ec-4f04-84c7-8230c84aa7f6'
    );
  });
});
