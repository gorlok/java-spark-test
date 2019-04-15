FROM oracle/graalvm-ce:1.0.0-rc15

COPY target/ /app
COPY localhost.jks /app/

ENV java.library.path=/opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64/libsunec.so

RUN cp /opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64/libsunec.so /app

WORKDIR /app

RUN native-image --no-server --enable-https --enable-all-security-services -jar demo-0.0.1-SNAPSHOT.jar 

ENV java.library.path=/opt/graalvm-ce-1.0.0-rc15/jre/lib/amd64/

ENTRYPOINT ["./demo-0.0.1-SNAPSHOT"]
