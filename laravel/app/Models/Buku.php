<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Buku extends Model
{
    protected $table = "buku";
    protected $fillable = [
        'judul',
        'tanggal_publikasi', 
        'author_id',
        'deskripsi',
        'rating',
        'cover_image_url',
    ];

    protected $casts = [
        'tanggal_publikasi' => 'date',
        'rating' => 'float',
    ];

    public function author() {
        return $this->belongsTo(Author::class, 'author_id');
    }
    
    public function genres()
    {
        return $this->belongsToMany(Genre::class, 'buku_genre', 'buku_id', 'genre_id');
    }
}
