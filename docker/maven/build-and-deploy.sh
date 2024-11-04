#!/bin/bash
set -eo pipefail

PATH_ROOT=$(cd ../.. && pwd)
PATH_PROJECT=$(cd ../.. && pwd)
TAG_CONTACT_DOCKER=3.26
# Boucle sur les arguments
for arg in "$@"; do
  # Récupère la clé et la valeur en utilisant le séparateur "="
  key=$(echo "$arg" | cut -f1 -d=)
  value=$(echo "$arg" | cut -f2 -d=)

  # Stocke la valeur dans une variable portant le nom de la clé
  declare $key="$value"
done

SERVICE_NAME=$(yq '.variables.IMAGE_NAME' $PATH_ROOT/.gitlab-ci.yml)
IMG_NAME="registry-scl-staging.svc.meshcore.net/sandbox/${USER}${SERVICE_NAME}"
if [ -z "$IMG_TAG" ]; then
    IMG_TAG="0.0.1"
fi

if [ -z "$IMG_PUSH" ]; then
    IMG_PUSH=true
fi


docker build -f $PATH_PROJECT/docker/Dockerfile $PATH_PROJECT -t $SERVICE_NAME -t $IMG_NAME:$IMG_TAG --build-arg TAG_CONTACT_DOCKER=$TAG_CONTACT_DOCKER

if $IMG_PUSH
then
    docker push  $IMG_NAME:$IMG_TAG
    echo "image pushed $IMG_NAME:$IMG_TAG"
fi