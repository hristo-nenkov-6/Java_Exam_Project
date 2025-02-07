# Short introduction
Project is still in production.
This is a client-server project for a similar to Github application.
It uses Editor, Commit, Server classes (Client will be avalible soon).
The application is console and will be looking like that (similar application):
![](Additional/JavaRecordingGif.gif)

# What is used
This applicatation uses multithread programming.
Synchronisation of threads is made by locking a mutex object.
Server and client are connected by ServerSocket and Socket on port 8080.
The communication between them is not made by any protocol(check ![](serverTCPclient), ![](serverUDPclient)).
Just using StreamWriters, StreamReaders, Console, FIleInputStream and FileOutputStream.
