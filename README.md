# android-jetpack-dicoding
Submission (3 / 3) Pemanfaatan fitur unit testing dan instrumental testing. Video penggunaan aplikasi dapat dilihat pada https://youtu.be/sv9U-5QmuXs.

Pada video diatas, pertama dijalankan aplikasi seperti biasa dengan menggunakan internet untuk mengambil data menggunakan Retrofit, kedua 
aplikasi dijalankan kembali tanpa adanya internet, data tetap ada karena sudah disimpan ke dalam Room. Program menggunakan repository untuk 
memproses pengambilan data secara otomatis (update melalui internet ataupun langsung mengambil dari database). Pemanfaatan LiveData dan Paging,
namum untuk kasus Paging tidak maksimal karena pengambilan data dari internet tidak menggunakan paging (api tmdb data satuan yang diambil aplikasi tidak memiliki field 'page', saya telat menyadari bahwa daftar popular memiliki field 'page', namum karena melanjutkan submission 2 yang tidak menggunakan daftar popular, sehingga pengambilan data dari internet tidak menggunakan paging, hanya Room saja yang menggunakan paging). Setelah itu, film / tv show dapat dijadikan favorite dan dimasukkan ke dalam database secara asynchronous. Untuk dependency injection menggunakan library bantuan 'Koin'. Dependency injection dibutuhkan agar program menjadi lebih singkat dan tetap menerapkan prinsip 'SOLID' (Singleton, Open-close, Liskov substitution, Interface segregation, Dependency Inversion)

Tahap selanjutnya dilakukan unit testing dan instrumental testing.

**Berikut kriteria submission yang harus Anda penuhi:**

# Submission 1

**Daftar film**

Syarat:
Terdapat 2 (dua) halaman yang menampilkan daftar film (Movies dan TV Show) dengan jumlah minimal 10 item.
Menerapkan ViewModel untuk menampung data Movies dan TV Show.

**Detail film**

Syarat:
Menampilkan poster dan informasi film pada halaman detail film.
Menerapkan ViewModel untuk menampung detail film.

**Unit Test**

Syarat:
Menerapkan unit test pada semua fungsi yang digunakan untuk mendapatkan data Movie dan TV Show.

**Instrumentation Test**

Syarat:

Menerapkan instrumentation test untuk memastikan fitur-fitur yang ada berjalan dengan semestinya.
Tuliskan skenario instrumentation test pada kolom Catatan atau berkas tersendiri (jangan lupa sampaikan juga di kolom Catatan agar reviewer dapat mengetahuinya) ketika Anda ingin mengumpulkan tugas ini. Untuk format penulisan skenario pengujian, Anda bisa melihat contoh di modul Proyek Academy : Pengujian ViewModel.

# Submission 2

**Daftar film**

Syarat:

Mempertahankan fitur sebelumnya.
Menerapkan ViewModel, LiveData dan Repository.

**Detail film**

Syarat:

Mempertahankan fitur sebelumnya.
Menerapkan ViewModel, LiveData dan Repository.

**Unit Test**

Syarat:

Menerapkan unit test pada semua fungsi yang digunakan untuk mendapatkan data Movie dan Tv Show dari API atau Lokal.

**Instrumentation Test**

Syarat:

Menerapkan instrumentation test untuk memastikan fitur-fitur yang ada berjalan dengan semestinya.
Jika pada aplikasi terdapat proses asynchronous, maka Anda wajib menerapkan Idle Resources.
Tuliskan skenario instrumentation test pada kolom Catatan atau berkas tersendiri (jangan lupa sampaikan juga di kolom Catatan agar reviewer dapat mengetahuinya) ketika Anda ingin mengumpulkan tugas ini. Untuk format penulisan sekenario pengujian, Anda bisa melihat contoh di modul Proyek Academy : Pengujian ViewModel.

# Submission 3 #

**Mempertahankan fitur sebelumnya.**

**Favorite Film**

Syarat:

Dapat menyimpan film ke database favorite.
Dapat menghapus film dari database favorite.
Terdapat halaman untuk menampilkan daftar Favorite Movies.
Terdapat halaman untuk menampilkan daftar Favorite TV Show.
Menerapkan Room menyimpan data Favorite Movie dan Favorite TVShow.
Menerapkan Pagination untuk mengatur data pada RecyclerView.

**Unit Test**

Syarat:
Menerapkan unit test pada fungsi yang ada pada kelas Repository dan ViewModel.

**Instrumentation Tests**
Syarat:

Menerapkan instrumentation test untuk memastikan fitur-fitur yang ada berjalan dengan semestinya.
Jika pada aplikasi terdapat proses asynchronous, maka Anda wajib menerapkan Idle Resources.
Tuliskan skenario instrumentation test pada kolom Catatan atau berkas tersendiri (jangan lupa sampaikan juga di kolom Catatan agar reviewer dapat mengetahuinya) ketika Anda ingin mengumpulkan tugas ini. Untuk format penulisan sekenario pengujian, Anda bisa melihat contoh di modul Proyek Academy : Pengujian ViewModel.

