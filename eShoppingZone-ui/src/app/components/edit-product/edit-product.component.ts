import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs';
import { Alert } from 'src/app/models/Alert.model';
import { Product } from 'src/app/models/Product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css'],
})
export class EditProductComponent implements OnInit, OnDestroy {
  public isLoading: boolean = false;
  public product: Product = new Product('', '', '', {}, {}, '', 0, '', {}, 0);
  public specification: [{ key: string; value: string }] = [
    { key: 'Specification Key', value: 'Specification Value' },
  ];
  public alert: Alert = new Alert('', '');
  public productSubscription: Subscription = new Subscription();

  constructor(
    private productService: ProductService,
    public route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      let productId = params.get('productId');
      this.productSubscription = this.productService
        .getProductById(productId)
        .subscribe((response) => {
          this.product = <Product>response.body;
          this.specification.pop();
          for (let specKey in this.product.specification) {
            this.specification.push({
              key: specKey,
              value: this.product.specification[specKey],
            });
          }
          this.isLoading = false;
          console.log(this.product);
        });
    });
  }

  ngOnDestroy(): void {
    this.productSubscription.unsubscribe();
  }

  public addSpecificationInput() {
    this.specification.push({
      key: 'Specification Key',
      value: 'Specification Value',
    });
  }

  public submitProductForm() {
    for (let spec of this.specification) {
      if (
        spec.key !== 'Specification Key' &&
        spec.value !== 'Specification Value'
      ) {
        this.product.specification[spec.key] = spec.value;
      }
    }
    this.isLoading = true;
    console.log(this.product);
    this.productService.updateProduct(this.product).subscribe(
      (res) => {
        console.log(res);
        if (res.status === 200) {
          this.alert = new Alert(
            'info',
            'Product Information Updated Successfully'
          );
        } else {
          this.alert = new Alert(
            'error',
            'Error in Updating Product Information'
          );
        }
        this.isLoading = false;
      },
      (error) => {
        this.alert = new Alert('error', error);
        this.isLoading = false;
      }
    );
  }
}
