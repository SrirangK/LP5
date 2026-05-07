import socket
import threading
import time

# CHANGE IP ADDRESS
next_ip = ""

# CHANGE PORTS
my_port = 8081
next_port = 8080

# True only for first machine
create_token = False


def critical_section():
    print("Entering Critical Section")
    time.sleep(5)
    print("Exiting Critical Section")


def pass_token():
    s = socket.socket()
    
    while True:
        try:
            s.connect((next_ip, next_port))
            break
        except:
            time.sleep(1)

    s.send("TOKEN".encode())
    print("Token Passed")

    s.close()


def receive_token():
    server = socket.socket()

    server.bind(("", my_port))
    server.listen(1)

    print("Waiting for Token...")

    while True:
        conn, addr = server.accept()

        token = conn.recv(1024).decode()

        if token == "TOKEN":
            print("Token Received")

            critical_section()

            pass_token()

        conn.close()


if create_token:
    time.sleep(5)
    pass_token()


threading.Thread(target=receive_token).start()


