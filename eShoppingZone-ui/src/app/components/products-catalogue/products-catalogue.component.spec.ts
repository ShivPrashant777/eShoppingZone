import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsCatalogueComponent } from './products-catalogue.component';

describe('ProductsCatalogueComponent', () => {
  let component: ProductsCatalogueComponent;
  let fixture: ComponentFixture<ProductsCatalogueComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [ProductsCatalogueComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductsCatalogueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should check the loading spinner', () => {
    let fixture = TestBed.createComponent(ProductsCatalogueComponent);
    let app = fixture.debugElement.componentInstance;
    app.isLoading = true;
    fixture.detectChanges();
    let compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.sr-only').textContent).toEqual(
      'Loading...'
    );
  });
});
