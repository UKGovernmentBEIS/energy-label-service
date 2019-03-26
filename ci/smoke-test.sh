#!/bin/bash

APP_TEMP_ROUTE=$1
CHECK_PATH="categories"
echo "Smoke testing $APP_TEMP_ROUTE"

TMPOUT=$(mktemp)
trap "rm -f \"$TMPOUT\""0 1 2 3 15

http_code=$(curl -s -o $TMPOUT $APP_TEMP_ROUTE/$CHECK_PATH -L --w "%{http_code}")

if [[ $http_code -eq 200 ]]; then
  echo "GET $CHECK_PATH success"
  exit 0
else
  echo "ERROR: GET $CHECK_PATH returned unexepcted code $http_code"
  cat $TMPOUT
  exit 1
fi