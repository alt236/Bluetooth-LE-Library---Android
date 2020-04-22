#!/bin/sh

set -e
set -o xtrace
java -jar ./buildsystem/apkdetails/apkdetails-1.2.2.jar "$@"
set +o xtrace