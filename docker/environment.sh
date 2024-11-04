#!/bin/bash
set -eo pipefail

if [ $1 == "generate" ]
then
    echo -n "tomcat:11.0.0-" > /install/env/tomcat_version
    echo -n "/usr/local/tomcat" > /install/env/catalina_home
    echo -n "${JAVA_HOME}" > /install/env/java_home
elif [ $1 == "export" ]
then
    TOMCAT_VERSION=$(cat /install/env/tomcat_version)
    export TOMCAT_VERSION
    CATALINA_HOME=$(cat /install/env/catalina_home)
    export CATALINA_HOME
    JAVA_HOME=$(cat /install/env/java_home)
    export JAVA_HOME
    if [[ "${JAVA_OPTS}" == "${JAVA_OPTS_DEFAULT}" ]] || [[ "${JAVA_OPTS}" == "" ]]
    then
        JAVA_OPTS="${JAVA_OPTS_DEFAULT}"
        echo "using default JAVA_OPTS=${JAVA_OPTS}  you can override the env value if needeed"
        export JAVA_OPTS
    fi
fi
