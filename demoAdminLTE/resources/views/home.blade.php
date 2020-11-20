@extends('layouts.app')

@section('content')
    
    <div class="container">
        <h1>Profile</h1>
        <hr>
        <div class="row">
            <!-- left column -->
            <div class="col-md-3">
                <div class="text-center">
                    <img src="{{ Auth::user()->getFirstMediaUrl('avatars', 'thumb') }}"
                        class="avatar img-circle" alt="avatar">
                </div>
            </div>

            <!-- edit form column -->
            <div class="col-md-9 personal-info">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Name:</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" value="{{ $user->name }}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Email:</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" value="{{ $user->email }}" disabled>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Created at:</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" value="{{ $user->created_at }}"
                                disabled>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

@endsection
