import { Injectable } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';

@Injectable({
  providedIn: 'root',
})
export class ImageUploadService {
  constructor(private storage: AngularFireStorage) {}

  uploadImage(file: File): Promise<string> {
    const filePath = `images/${file.name}`;
    const fileRef = this.storage.ref(filePath);
    const task = this.storage.upload(filePath, file);

    return new Promise((resolve, reject) => {
      task.snapshotChanges().subscribe(
        (snapshot) => {
          if (snapshot && snapshot.state === 'success') {
            fileRef.getDownloadURL().subscribe((url) => {
              resolve(url);
            });
          }
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
}
