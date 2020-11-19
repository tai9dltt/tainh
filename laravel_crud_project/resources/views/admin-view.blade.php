@extends('layouts.app')

@section('content')
    <div class="container mt-5">
        <h3 class="text-center font-weight-bold">Datatable Server Side in Laravel 7 </h3>
        <table class="table mt-4" id="usersTable">
            <thead>
                <th> ID</th>
                <th> Name </th>
                <th> Email </th>
                <th> Role ID </th>
                <th> Created_at </th>
                <th> Updated_at </th>
                {{-- <th> Edit </th> --}}
            </thead>
        </table>
    </div>


    <script type="text/javascript">
        $(document).ready(function() {
            var table = $('#usersTable').DataTable({
                processing: true,
                serverSide: true,
                ajax: "{{ route('admin.getUsers') }}",
                columns: [{
                        data: 'id',
                        name: 'id'
                    },
                    {
                        data: 'name',
                        name: 'name'
                    },
                    {
                        data: 'email',
                        name: 'email'
                    },
                    {
                        data: 'role_id',
                        name: 'role_id'
                    },
                    {
                        data: 'created_at',
                        name: 'created_at'
                    },
                    {
                        data: 'updated_at',
                        name: 'updated_at'
                    },
                    // {data: 'action', name: 'action', orderable: false, searchable: false}

                ]
            });
        });

    </script>
@endsection
