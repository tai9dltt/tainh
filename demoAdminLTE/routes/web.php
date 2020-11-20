<?php

use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});
Auth::routes();

Route::get('/home', 'HomeController@index')->name('home');

Route::resource('users','UsersController');

Route::get('users/{id}/edit/','UsersController@edit');

Route::post('users/change/{id}', 'UsersController@changeStatus');

Route::get('/users', 'UsersController@index')->middleware('auth','isAdmin');