#!/bin/bash
## Get arguments

for ARGUMENT in "$@"; do
  KEY=$(echo $ARGUMENT | cut -f1 -d=)
  VALUE=$(echo $ARGUMENT | cut -f2 -d=)
  case "$KEY" in
    api)          API=${VALUE} ;; ## CF API Endpoint
    app)          APP=${VALUE} ;; ## Name of app
    org)          ORG=${VALUE} ;; ## Name of organisation
    space)        SPACE=${VALUE} ;; ## Name of space
    user)         USER=${VALUE} ;; ## CF user / email address
    manifest)     MANIFEST=${VALUE} ;; ## Path to manifest file
    test)         SMOKE_TEST=${VALUE} ;; ## Path to smoke test script
    delete)       DELETE_OLD_APPS=${VALUE} ;; ## Remvoe old/failed apps once complete
    *)
  esac
done

## Check environment
if [ -z $APP ]; then
  echo "APP Not set - error"; ERROR="true"
else
  echo "APP = $APP"
fi

if [ -z $ORG ]; then
  echo "ORG Not set - error"; ERROR="true"
else
  echo "ORG = $ORG"
fi

if [ -z $SPACE ]; then
  echo "SPACE Not set - error"; ERROR="true"
else
  echo "SPACE = $SPACE"
fi

if [ -z $API ]; then
  API="api.london.cloud.service.gov.uk"
  echo "API Not set - using default: $API"
else
  echo "API = $API"
fi

if [ -z $USER ]; then
  echo "USER Not set - will prompt"
else
  echo "USER = $USER"
  USER="-u $USER" ## Make it command ready
fi

# Get pass from env rather than cmd args
if [ -z $CF_PASS ]; then
  echo "PASS Not set - will prompt"
else
  echo "PASS = ***"
  PASS="-p $CF_PASS" ## Make it command ready
fi

if [ -z $MANIFEST ]; then
  MANIFEST="./manifest.yml"
  echo "MANIFEST Not set - using default: $MANIFEST"
  ## Check that manifest exists
  if [ ! -f $MANIFEST ]; then
    echo "MANIFEST: $MANIFEST - does not exist - error"; ERROR="true"
  fi
else
  echo "MANIFEST = $MANIFEST"
fi

if [ -z $SMOKE_TEST ]; then
  echo "SMOKE_TEST Not set - ignoring"
else
  echo "SMOKE_TEST = $SMOKE_TEST"
  ## Check that smoke test exists
  if [ ! -f $SMOKE_TEST ]; then
    echo "SMOKE_TEST: $SMOKE_TEST - does not exist - error"; ERROR="true"
  else
    SMOKE_TEST="--smoke-test $SMOKE_TEST"
  fi
fi

if [ ! -z $DELETE_OLD_APPS ] && [ $DELETE_OLD_APPS = "true" ]; then
  DELETE_OLD_APPS="--delete-old-apps"
fi

if [ ! -z $ERROR ]; then
  echo "Error encountered - exiting"
  exit 1
fi

echo "Installing cf CLI"
wget "https://cli.run.pivotal.io/stable?release=linux64-binary" -qO cf.tgz && tar -zxvf cf.tgz && rm cf.tgz
export PATH="$PATH:."
cf --version

echo "Installing cf blue-green deploy plugin"
cf add-plugin-repo CF-Community https://plugins.cloudfoundry.org
cf install-plugin blue-green-deploy -r CF-Community -f

echo "Logging in to Cloud Foundry"
cf login -a $API -o $ORG -s $SPACE $USER $PASS

echo "Starting Blue-Green Deploy"
cf bgd $APP -f $MANIFEST $DELETE_OLD_APPS $SMOKE_TEST

## Check return code and exit accordingly
if [ $? != 0 ]; then
  echo "Deploy failed - exit 1"
  exit 1
else
  echo "Deploy succeeded - exit 0"
  exit 0
fi