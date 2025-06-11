<?php

namespace App\Mail;

use App\Models\User;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue; // <-- Tambahkan ini
use Illuminate\Mail\Mailable;
use Illuminate\Mail\Mailables\Content;
use Illuminate\Mail\Mailables\Envelope;
use Illuminate\Queue\SerializesModels;

class VerifyUserEmail extends Mailable 
{

    public $user;
    public $verificationToken;

    public function __construct(User $user, string $verificationToken)
    {
        $this->user = $user;
        $this->verificationToken = $verificationToken;
    }

    public function envelope(): Envelope
    {
        return new Envelope(
            subject: 'Verifikasi Alamat Email Anda',
        );
    }

    public function content(): Content
    {
        return new Content(
            markdown: 'emails.verify-user-email',
            with: [
                'name' => $this->user->name,
                'verificationToken' => $this->verificationToken,
            ]
        );
    }

    public function attachments(): array
    {
        return [];
    }
}
