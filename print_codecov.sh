#!/bin/bash

PATTERN='jacoco*.xml'
JAR_FILE='./buildsystem/jacocoxml/jacocoxmlparser-1.0.0.jar'
COLOR_FLAG=''

if which tput > /dev/null 2>&1 && [[ $(tput -T${TERM} colors) -ge 8 ]]; then
    COLOR_FLAG='--color'
fi

find -iname ${PATTERN} -print0 \
    | sort -z \
    | xargs -0 -I '{}'  java -jar ${JAR_FILE} ${COLOR_FLAG} --global-stats -i '{}'

