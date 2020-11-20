<!-- need to remove -->
@if (Auth::user()->role_id !== 1)
    <li class="nav-item">
        <a href="{{ route('home') }}" class="nav-link active">
            <i class="nav-icon fas fa-home"></i>
            <p>Home</p>
        </a>
    </li>
@else
    <li class="nav-item">
        <a href="{{ route('home') }}" class="nav-link active">
            <i class="nav-icon fas fa-home"></i>
            <p>Home</p>
        </a>
    </li>
    <li class="nav-item">
        <a href="/users" class="nav-link ">
            <p>List Account</p>
        </a>
    </li>

@endif
