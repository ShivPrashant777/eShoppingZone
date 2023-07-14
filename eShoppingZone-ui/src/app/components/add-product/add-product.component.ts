import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { Alert } from 'src/app/models/Alert.model';
import { Product } from 'src/app/models/Product.model';
import { ImageUploadService } from 'src/app/services/image-upload.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css'],
})
export class AddProductComponent implements OnInit {
  public form: any;
  public isLoading: boolean = false;
  public product: Product = new Product('', '', '', {}, {}, '', 0, '', {}, 0);
  public alert: Alert = new Alert('', '');

  constructor(
    private formBuilder: FormBuilder,
    private productService: ProductService,
    private imageUploadService: ImageUploadService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      productName: ['', Validators.required],
      category: ['', Validators.required],
      productType: ['', Validators.required],
      description: ['', Validators.required],
      price: [0, Validators.required],
      specification: this.formBuilder.array([this.createSpecificationGroup()]),
    });
  }

  // Upload images to firebase
  onFileSelected(event: any) {
    const file: File = event.target.files[0];

    if (file) {
      this.imageUploadService
        .uploadImage(file)
        .then((url) => {
          console.log('Image uploaded. URL:', url);
          this.product.image = url;
        })
        .catch((error) => {
          console.error('Error uploading image:', error);
        });
    }
  }

  // Helper method to create a specification form group
  createSpecificationGroup() {
    return this.formBuilder.group({
      key: ['', Validators.required],
      value: ['', Validators.required],
    });
  }

  get specificationForms() {
    return this.form.get('specification') as FormArray;
  }

  addSpecification() {
    this.specificationForms.push(this.createSpecificationGroup());
  }

  removeSpecification(index: number) {
    this.specificationForms.removeAt(index);
  }

  public onSubmit() {
    this.product.productType = this.form.value.productType;
    this.product.productName = this.form.value.productName;
    this.product.category = this.form.value.category;
    this.product.description = this.form.value.description;
    this.product.price = this.form.value.price;

    for (let spec of this.form.value.specification) {
      if (spec.key !== '' && spec.value !== '') {
        this.product.specification[spec.key] = spec.value;
      }
    }

    this.isLoading = true;
    console.log(this.product);
    this.productService.addProduct(this.product).subscribe(
      (res) => {
        console.log(res);
        if (res.status === 200) {
          this.alert = new Alert('info', 'Product Added Successfully');
        } else {
          this.alert = new Alert('error', 'Error in Adding Product');
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
