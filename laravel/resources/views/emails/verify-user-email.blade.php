<x-mail::message>
# Verifikasi Alamat Email Anda

Halo {{ $name }},

Terima kasih telah mendaftar di aplikasi kami. Untuk mengaktifkan akun Anda, mohon verifikasi alamat email Anda dengan memasukkan kode berikut di aplikasi:

**Kode Verifikasi Anda:** **{{ $verificationToken }}**

Kode ini akan kadaluarsa dalam **10 menit**.

Jika Anda tidak merasa mendaftar di aplikasi ini, mohon abaikan email ini.

Terima kasih,
{{ config('app.name') }}
</x-mail::message>