import socket
import threading
import time

# ==========================
# CHANGE THESE VALUES
# ==========================

MY_PORT = 5000

NEXT_IP = "192.168.1.11"
NEXT_PORT = 5001

CREATE_TOKEN = True

# ==========================


# Function to execute Critical Section
def critical_section():
    print("\n>>> ENTERING CRITICAL SECTION <<<")
    time.sleep(5)
    print("<<< EXITING CRITICAL SECTION >>>\n")


# Function to pass token to next machine
def pass_token():
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    while True:
        try:
            client.connect((NEXT_IP, NEXT_PORT))
            break
        except:
            print("Waiting for next machine...")
            time.sleep(1)

    client.send("TOKEN".encode())

    print("Token passed to next machine")

    client.close()


# Function to receive token
def receive_token():
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    server.bind(("0.0.0.0", MY_PORT))

    server.listen(1)

    print(f"Listening on port {MY_PORT}...")

    while True:
        conn, addr = server.accept()

        token = conn.recv(1024).decode()

        if token == "TOKEN":
            print("\nToken received")

            critical_section()

            time.sleep(2)

            pass_token()

        conn.close()


# Start server thread
threading.Thread(target=receive_token).start()

# Initial token creation
if CREATE_TOKEN:
    time.sleep(5)

    print("\nCreating Initial Token...\n")

    pass_token()