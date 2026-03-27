# bsp-rn — Network Programming Samples (UDP, TCP, Sockets)

---

## UDPChat

A minimal UDP-based chat demo in Java. A **server** listens on port 50000 for a single datagram, wraps the received text in `$…$`, and sends it back to the client on port 50001. A **client** sends a line of text to the server and prints the echoed reply.

### Compile

```bash
javac UDPChat.java
```

### Run

Start the server first, then the client.

**Server**
```bash
java UDPChat s
```

**Client (localhost)**
```bash
java UDPChat c
```

**Client (remote host)**
```bash
java UDPChat c <address>
```

Replace `<address>` with the hostname or IP of the machine running the server.

### Ports used

| Role           | Port  |
|----------------|-------|
| Server listens | 50000 |
| Client listens | 50001 |
