#!/bin/bash -x
set -eo pipefail

. /install/env/environment.sh export

## Waiting for CNI, must create MYS_CNI_docker database
#if $WAIT_FOR_CNI ; then dockerize-wait -wait http://cni:8080/commercialNumbersIntegrator/commercial-number-integrator/rules -timeout 2h ; fi

#setup only on first start
if [ ! -f ${CATALINA_BASE}/firstRun ]; then
    /install/install-scripts/install-artifact.sh
    touch ${CATALINA_BASE}/firstRun
fi
#start catalina
CMD="$@"
eval "$CMD"
