#!/bin/bash

echo "--- uploader start ---";

TOKEN=$1
SRC_FILE=$2
FILE_NAME=basename $SRC_FILE
DST_FILE=$3

curl -f -I -X POST \
       -H "Authorization: Bearer $TOKEN" \
       -T  "$SRC_FILE" \
       "https://content.dropboxapi.com/1/files_put/auto/$DST_FILE/$FILE_NAME"

echo "\n--- uploader end ---";