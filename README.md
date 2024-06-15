# microservice
# using self generated cert
/etc/nginx/nginx.conf
add

    server {
        listen       443 ssl;
        server_name  localhost;

        ssl_certificate      /root/ca.crt;
        ssl_certificate_key  /root/ca.key;
        include conf.d/elasticbeanstalk/*.conf;
    }


systemctl stop nginx
systemctl start nginx
