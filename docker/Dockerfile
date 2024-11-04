FROM tomcat:11.0.0-jdk17-temurin


USER root

ENV CATALINA_BASE=/MIDDLE/CBW/joboffer-consumer
RUN mkdir -p ${CATALINA_BASE} \
    && chown -R 9000 ${CATALINA_BASE}

WORKDIR ${CATALINA_BASE}

RUN mkdir -p /install/{env,artifacts,config-files,pre-install-scripts,work,post-install-scripts};
# Copy config files
COPY /docker/config-files /install/config-files
COPY /docker/pre-install-scripts /install/pre-install-scripts
#COPY /docker/post-install-scripts /install/post-install-scripts
COPY /docker/install/install-artifact.sh /install/install-scripts/install-artifact.sh
COPY /docker/environment.sh /install/env/environment.sh
COPY /docker/entrypoint.sh /entrypoint.sh

RUN apt-get update && \
    apt-get install -y unzip && \
    rm -rf /var/lib/apt/lists/*

RUN chgrp -R 0 /entrypoint.sh \
    && chmod 775 /entrypoint.sh \
    && chmod +x /install/*/*.sh \
    && find /install -not -group 0 -exec chgrp -R 0 {} \; \
    && /install/env/environment.sh generate \
    && find ${CATALINA_HOME} -not -group 0 -exec chgrp -R 0 {} \; \
    && find ${CATALINA_HOME} -exec bash -c '[ "$(stat -c %a {} | cut -c 1)" != "$(stat -c %a {}  | cut -c 2)" ]' \; -exec chmod g=u {} \;

# copy artifacts
COPY --chown=9000:server target/joboffer-consumer-emailnotification.war /install/artifacts/joboffer-consumer.war


EXPOSE 8080/tcp
EXPOSE 8443/tcp

ENTRYPOINT ["/entrypoint.sh"]
CMD ["$CATALINA_HOME/bin/catalina.sh", "run"]

USER 9000

ENV LOG_LEVEL=DEBUG

VOLUME /tmp
