<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Buku extends Model
{
    protected $table = "buku";
    protected $fillable = [
        'judul',
        'genre',
        'tanggal_publikasi', 
        'author_id',
        'deskripsi',
        'rating',
    ];

    protected $casts = [
        'tanggal_publikasi' => 'date',
        'rating' => 'float',
    ];

    public function author() {
        return $this->belongsTo(Author::class, 'author_id');
    }
}
