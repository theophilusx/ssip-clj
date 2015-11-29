# ssip-clj

A very simple clojure library to interface with a SSIP client (Speech Synthesis
Interface Protocol), for example speech-dispatcher
<http://devel.freebsoft.org/speechd>

My aim is to develop this as a useful library for some of my own projects where I
would like the application to provide some speech feedback. At this stage, it is
really just a proof of concept. Essentially, I spent 4 hours hacking this together
today and now I can see it works, hope to find time to turn it into a real library.

## Usage

This library uses TCP to communicate with the speech-dispatcher server. Therefore,
you must have speech-dispatcher setup to run using TCP and not UNIX sockets.

The easiest way to play with it at this time is to just clone the repo. Currently,
this library is at a very early stage. Basically, I spent 4 hours putting it
together, so don't expect much. Might help if you want some examples on interfacing
with speech-dispatcher or using sockets with Clojure.

Basically, the library uses core.async and setups up a basic socket to the
speech-dispatcher server. You can then send data via core.async channels and get
feedback from the server via another core.async channel.

This is my first time using core.async, so I've probably got it all wrong! Feedback
and suggestions welcome. 

## License

Copyright © 2015 Tim Cross

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
