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


Route::group(['middleware' => ['auth', 'isAdmin']], function () {
    Route::get('admin-view', 'HomeController@adminView')->name('admin.view');
});

Route::get('admin/getUsers/', 'HomeController@getUser')->name('admin.getUsers');

Route::get('admin', 'HomeController@adminIndex');



// Route::controller('admin', 'HomeController', [
//     'getUser'  => 'admin.data',
//     'adminIndex' => 'admin',
// ]);
