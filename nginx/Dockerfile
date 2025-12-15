FROM nginx:alpine

# Install OpenSSL
RUN apk add --no-cache openssl

# Copy NGINX configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Create SSL directory and generate self-signed certificates
RUN mkdir -p /etc/nginx/ssl && \
    openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/nginx/ssl/self-signed.key \
    -out /etc/nginx/ssl/self-signed.crt \
    -subj "/C=US/ST=State/L=City/O=Heditra/CN=localhost"

# Create log directory
RUN mkdir -p /var/log/nginx

# Expose HTTP and HTTPS ports
EXPOSE 80 443

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost/health || exit 1

CMD ["nginx", "-g", "daemon off;"]
