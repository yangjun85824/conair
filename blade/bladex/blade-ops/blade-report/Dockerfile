FROM bladex/alpine-java:openjdk17_cn_slim

LABEL maintainer="bladejava@qq.com"

RUN mkdir -p /blade/report

WORKDIR /blade/report

EXPOSE 8108

COPY ./target/blade-report.jar ./app.jar

ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
