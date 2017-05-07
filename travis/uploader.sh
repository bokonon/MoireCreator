#!/bin/bash

echo '--- uploader start ---';

TOKEN=$1
SRC_FILE=$2
FILE_NAME=basename $SRC_FILE
DST_FILE=$3
DATE=Asia/Tokyo date +"%Y%m%d-%H%M%S"

curl -f -I -X POST \
       -H "Authorization: Bearer $TOKEN" \
       -T  "$SRC_FILE" \
       "https://content.dropboxapi.com/1/files_put/auto/$DST_FILE/${DATE}/$FILE_NAME"

if [ "echo $FILE_NAME|grep debug" ]; then
       echo '--- uploader mapping.text start ---';

       curl -f -I -X POST \
              -H "Authorization: Bearer $TOKEN" \
              -T "./app/build/outputs/mapping/release/mapping.txt" \
              "https://content.dropboxapi.com/1/files_put/auto/$DST_FILE/${DATE}/mapping.txt"

       echo '--- uploader mapping.text end ---';
fi

echo '--- uploader end ---';