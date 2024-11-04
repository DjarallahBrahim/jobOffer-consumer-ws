#!/bin/bash
set -e

mkdir -p ${CATALINA_BASE}/{bin,conf,logs,temp,webapps,work}
mkdir -p ${CATALINA_BASE}/logs/archives

cp $CATALINA_HOME/conf/server.xml ${CATALINA_BASE}/conf

cp $CATALINA_HOME/conf/web.xml ${CATALINA_BASE}/conf

#cp /install/config-files/tomcat/server.xml $CATALINA_BASE/conf/

cp $CATALINA_HOME/conf/tomcat-users.xml ${CATALINA_BASE}/conf

cp $CATALINA_HOME/conf/catalina.properties ${CATALINA_BASE}/conf

#sed -i '/^common.loader/s=.*=&,/usr/-tomcat-components2/*.jar=' ${CATALINA_BASE}/conf/catalina.properties

mkdir -p ${CATALINA_BASE}/webapps/joboffer-consumer
