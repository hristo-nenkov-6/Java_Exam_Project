# Short introduction
The project is still in production.
This is a client-server project for similar to Github application.
The application is a console and will look like that (similar application):

![](Additional/JavaRecordingGif.gif)

It uses Editor, Commit, Server classes (Client will be available soon).

# What is used
This application uses multithread programming.
Synchronization of threads is made by locking a mutex object.
The server and client are connected by ServerSocket and Socket on port 8080.
The communication between them is not made by any protocol(check the Technical university Java Repository for TCP and UDP client information).
Just using StreamWriters, StreamReaders, Console, FIleInputStream and FileOutputStream.
