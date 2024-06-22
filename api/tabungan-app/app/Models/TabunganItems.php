<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TabunganItems extends Model
{
    use HasFactory;

    protected $table = 'tabungan_items';

    public function tabungan() {
        return $this->belongsTo(Tabungan::class);
    }
}
