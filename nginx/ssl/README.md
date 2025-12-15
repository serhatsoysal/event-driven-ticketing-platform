# SSL Certificate Generation

To generate self-signed SSL certificates, run:

```bash
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout self-signed.key \
  -out self-signed.crt \
  -subj "/C=US/ST=State/L=City/O=Heditra/CN=localhost"
```

Or use the Docker container to generate them:

```bash
docker run --rm -v $(pwd):/certs alpine/openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout /certs/self-signed.key \
  -out /certs/self-signed.crt \
  -subj "/C=US/ST=State/L=City/O=Heditra/CN=localhost"
```
