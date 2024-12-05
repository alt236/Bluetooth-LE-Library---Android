#!/bin/sh

#
# This script scans the directory passed as the first parameter and hashes all gradlefiles it finds/
# It then outputs the hashes into the file passed as the second parameter
#

SOURCE_DIR=$1
HASH_FILE=$2
REGEX_PATTERN_GRADLE="*\.gradle"
REGEX_PATTERN_ROBOLECTRIC="*robolectric.properties"
REGEX_PATTERN_VERSION_CATALOG="*libs\.versions\.toml"

find "${SOURCE_DIR}" -type f \( -iname "$REGEX_PATTERN_GRADLE" -o -iname "$REGEX_PATTERN_ROBOLECTRIC" -o -iname "$REGEX_PATTERN_VERSION_CATALOG" \) -exec md5sum {} \; | sort -k2 -b > "${HASH_FILE}"
