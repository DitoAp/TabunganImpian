<?php

use App\Http\Controllers\TabunganController;
use App\Http\Controllers\TabunganItemsController;
use Illuminate\Support\Facades\Route;

// Route::prefix('/tabungan')->group(function() {
//     Route::get('/' ,[TabunganController::class, 'getData']);
//     Route::get('/tercapai' ,[TabunganController::class, 'getSuccess']);
//     Route::post('/' ,[TabunganController::class, 'store']);
//     Route::put('/{id}' ,[TabunganController::class, 'update']);
//     Route::get('/{id}' ,[TabunganController::class, 'destroy']);
// });

// Route::prefix('/items')->group(function() {
//     Route::get('/{id}', [TabunganItemsController::class, 'getData']);
//     Route::post('/increment', [TabunganItemsController::class, 'store']);
//     Route::post('/decrement', [TabunganItemsController::class, 'put']);
//     Route::get('/{id}', [TabunganItemsController::class, 'destroy']);
// });