#Protocol objectives: what does the protocol do?
As a client I should be able to send an operation to the server and get my result back.

##Overall behavior:
###What transport protocol do we use?
TCP
###How does the client find the server (addresses and ports)?
Local address and ports it's the same
###Who speaks first?
The client
###Who closes the connection and when?
The server when the client say that he doesn't want to continue

##Messages:
###What is the syntax of the messages?
The client write a number, a sign and another number
The server respond with the result of the operation
###What is the sequence of messages exchanged by the client and the server? (flow)
C-S alternated 
###What happens when a message is received from the other party? (semantics)
when the client receive the message from the client it checks if the arguments are effectively numbers (otherwise it returns an error) then it transforms the numbers in double and keeps the operator to use it
in a switch to choose the operation to do.
the client simply read the message.
##Specific elements (if useful)
###Supported operations
addition, multiplication, subtraction, division, modulo
###Error handling
If the client gives a letter instead of a number, the server should give an error, or if the client gives an operation that is not valid
###Extensibility
Examples: examples of some typical dialogs.

C> nc to start the server
S> I'm a calculator, the supported operations are +,-,*,/,%
C> 3 + 5
S> 8
S> BYE
C> close connection