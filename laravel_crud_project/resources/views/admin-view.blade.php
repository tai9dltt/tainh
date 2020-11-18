@extends('layouts.app')

@section('content')
<div class="container">
    <table class="table table-bordered" id="myTable">
        <thead>
            <tr>
                <th>No</th>
                <th>Name</th>
                <th>Email</th>
                <th width="100px">Action</th>
            </tr>
        </thead>

      
    </table>
</div>

<script type="text/javascript">
    $(document).ready(function(){

     var table = $('#myTable').DataTable({
         processing: true,
         serverSide: true,
         ajax: "{{ route('admin.admin-view') }}",
         columns: [
             {data: 'id', name: 'id'},
             {data: 'name', name: 'name'},
             {data: 'email', name: 'email'},
             {data: 'action', name: 'action', orderable: false, searchable: false},
         ]
     });
     
   });
 </script>
@endsection

