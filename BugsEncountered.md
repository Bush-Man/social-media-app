How  will auth tokens stored in DataStore be loaded in OkHttp  without using run Blocking, 
OkHttp is synchronous so we should not use suspend function within this block ? Using cache string has a problem 
because if you switch off the phone or clear RAM the auth tokens loaded from the string cache memory is cleared this 
makes the Authorization Bearer token to become null but the user is not logged out since the Authorization Bearer token 
is still available in DataStore.
