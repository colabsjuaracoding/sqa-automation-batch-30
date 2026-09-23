# Aturan Keyword Khusus

## 1. COMMIT DAN PUSH
Ketika USER mengetikkan `COMMIT DAN PUSH`, kamu harus melakukan operasi git commit (tambahkan semua perubahan yang ada) dan push ke repositori yang didefinisikan di file `.env`. 
Gunakan data berikut dari file `.env`:
- `GIT_PAT`: Token yang ada di variabel `GIT_PAT` pada file `.env`
- `GIT_REPO`: URL repositori yang ada di variabel `GIT_REPO` pada file `.env`
(Format remote URL dengan akses token: `https://<GIT_PAT>@<URL_REPO_TANPA_HTTPS>`)

## 2. Aturan Pembuatan Keyword Baru
Apapun keyword yang dibuat oleh USER di dalam project ini, kamu **WAJIB** menuliskannya ke dalam file di root project yaitu `keyword.md`. 
Aturan penulisan di `keyword.md`:
- Hanya boleh berisi keyword dan referensi detil (link/path) ke file instruksi yang terkait.
- **DILARANG** menambahkan informasi tambahan ataupun penjelasan apapun tentang keyword tersebut di dalam file `keyword.md`.
