# sealcast
Simple ShoutCast server for broadcasting AAC written in Java

This simple Java web application is a ShoutCast server.
It listens for a socket connection for an AAC ADTS stream (not in an MP4 container).

- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Simple web interface to play stream with HTML 5.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Play on ShoutCast compatible players. VLC etc.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Supports live AAC ADTS streams.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Simple TTY status panel showing connected listeners.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) "Rewind" to beginning of live broadcast.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Creates local copy of broadcast.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Sends clients a priming buffer and then continues stream real time.
- ![#1589F0](http://placehold.it/15/1589F0/000000?text=+) Can be used to relay other AAC shoutcast streams.

![sealcast](https://storage.googleapis.com/kyoto.catchpole.net/sealcast-turnable.jpg "sealcast")

I created this project as I wanted to do my own AAC shoutcast broadcasting but couldn't
find any simple, free solutions (that's not to say they don't exist).
The fundimentals of ShoutCast broadcasting are pretty simple. A client connects via
HTTP and the server sends a few headers and the audio stream. The complication of
dropping in on an audio stream at any point in time is that the audio should begin at
the correct frame offset in the stream. The server splits the incoming stream into ADTS frames
and then these frames are used as discreet segments which are queued for consumption by clients.
If a client pauses reading or gets behind, frames are simply dropped from its queue until it
catches up.

Sealcast also has a rewind feature allow you to listen to a stream from the beginning of the broadcast.
For live broadcasts sealcast currently relies on the real time stream to provide the inherent timing the clients.
A copy of the stream is saved along with a timing file which records the offset time at which each frame was
received by the server. On playback of the saved file, sealcast will use the timing file to apply the
appropriate timing to each frame. Sealcast doesn't let the client read it as fast as they like, throwing out your bandwidth
capacity planning for genuine listeners.

Sealcast can be packaged as a war and deployed to an application server.
The simplest way to start broadcasting though is to use the jetty plugin.
This will also allow you to see the status panel on the same terminal.

        mvn jetty:run

I have this all running on a Raspberry Pi, where I use a custom build of ffmpeg to
live stream from a Behringer UMC404 to sealcast, which is listening on port 9999 for
the AAC ADTS stream...

        ffmpeg -f alsa -ac 4 -ar 48000 -sample_fmt s16 \
               -i default:CARD=U192k -y -c:a libfdk_aac -b:a 96k \
               -f adts -c:a libfdk_aac -b:a 96k -ac 2 tcp://localhost:9999

Ping [catchapolay](https://twitter.com/catchapolay) on Twitter if you use sealcast to broadcast and I'll
listen in if i can.

Enjoy!

