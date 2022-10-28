# eRecruitment

## Key Activities

### **User Bagian**

1. Mengajukan Permintaan Penambahan SDM.
2. Memantau progres/realisasi atas permintaan yang diajukan.

### **User Recruitment**

1. Memasang loker berdasarkan permintaan Penambahan SDM sesuai dengan jabatan dan kriteria yang dibutuhkan
2. Secara otomatis, dari lamaran yang masuk akan membentuk daftar prioritas kandidat berdasarkan perbandingan
   antara kriteria yang dibutuhkan terhadap data diri kandidat.
3. Mengelola pemanggilan pelamar untuk melakukan psikotest dan wawancara.
4. Psikotest dan Wawancara adalah dua kegiatan yang tidak saling terkait dan tidak berurutan, bisa dilakukan keduanya,
   bisa hanya salah satu saja.
5. Mengelola daftar kehadiran psikotes dan/atau wawancara.
6. Mengelola hasil psikotes dan/atau wawancara.
7. Memberi informasi via email/via situs loker kepada kandidat terkait hasil wawancara, diterima atau tidak.

### **Pelamar**

1. Signup ke situs Loker.
2. Mengajukan lamaran ke posisi/jabatan yang diinginkan dan memasukkan data diri serta data lain yang dibutuhkan.
3. Menerima informasi hasil lulus atau tidaknya dalam proses test.

## Arsitektur & Fitur

1. Java Spring Boot untuk REST API.
2. Database menggunakan PostgreSQL.
3. JWT Spring Boot Security.
4. Apache POI untuk export data.
5. Web socket, notifikasi pada sisi admin dashboard ketika ada pelamar yang apply pada loker.
6. Schedule Task, untuk unpublish(close) loker yang telah melewati tanggal dealine.
7. Unit Testing
8. Swagger untuk dokumentasi API.

## How to install

1. Pull this repo.
2. Install all dependecies.
3. Create database and set your database name on properties.
4. Run : <pre>mvn spring-boot:run</pre>
5. Open browser and enter this <a href="http://127.0.0.1:8080/swagger-ui/">url http://127.0.0.1:8080/swagger-ui/ </a>
   to see the API documentation on swagger **or** you can access : https://e-recruitment-alterra.herokuapp.com/swagger-ui/#/

