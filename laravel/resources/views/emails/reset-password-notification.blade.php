<x-mail::message>
# Reset Kata Sandi

Halo {{ $name }},

Anda menerima email ini karena kami menerima permintaan reset kata sandi untuk akun Anda.

Kode reset kata sandi Anda adalah:

**{{ $token }}**

Kode ini akan kadaluarsa dalam **60 menit**.

Jika Anda tidak meminta reset kata sandi, Anda dapat mengabaikan email ini.

Terima kasih,
{{ config('app.name') }}
</x-mail::message>